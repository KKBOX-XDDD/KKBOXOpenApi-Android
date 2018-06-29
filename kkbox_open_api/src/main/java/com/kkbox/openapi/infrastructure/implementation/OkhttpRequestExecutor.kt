package com.kkbox.openapi.infrastructure.implementation

import com.kkbox.openapi.infrastructure.ApiSpec
import com.kkbox.openapi.infrastructure.RequestExecutor
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class OkhttpRequestExecutor(
        private val httpClient: OkHttpClient,
        private val syncMode: Boolean = false
) : RequestExecutor {

    private val executor = Executors.newScheduledThreadPool(3)

    override fun request(api: ApiSpec, failCallback: (error: Error) -> Unit, completeCallback: (response: ByteArray) -> Unit, tag: Any) {
        if (syncMode) {
            try {
                val response = httpClient.newBuilder()
                        .readTimeout(api.timeout, TimeUnit.MILLISECONDS)
                        .build().newCall(generateRequest(api, tag)).execute()
                if (response.isSuccessful) {
                    val result = response.body()?.bytes() ?: ByteArray(0)
                    completeCallback(result)
                } else {
                    val error = onResponseError(response)
                    failCallback(error)
                }
            } catch (ioException: IOException) {
                val error = Error(ioException)
                failCallback(error)
            }
            return
        }
        doAsync(executorService = executor) {
            try {
                val request = generateRequest(api, tag)
                val call = httpClient.newBuilder()
                        .readTimeout(api.timeout, TimeUnit.MILLISECONDS)
                        .build().newCall(request)
                call.isExecuted
                val response = call.execute()

                if (response.isSuccessful) {
                    val result = response.body()?.bytes() ?: ByteArray(0)
                    uiThread {
                        completeCallback(result)
                    }
                } else {
                    val error = onResponseError(response)
                    uiThread {
                        failCallback(error)
                    }
                }
            } catch (ioException: IOException) {
                val error = Error(ioException)
                uiThread {
                    failCallback(error)
                }
            }
        }
    }

    private fun onResponseError(response: Response): Error {
        return when (response.code()) {
            401 -> AuthError(response.message())
            else -> Error(response.toString())
        }
    }

    class AuthError(message: String) : Error(message)

    private fun generateRequest(api: ApiSpec, tag: Any): Request {
        val builder = Request.Builder()
                .headers(headers(api))
                .tag(tag)
        return when (api.httpMethod) {
            ApiSpec.HttpMethod.GET -> builder.get().url(httpUrlWithParameters(api))
            ApiSpec.HttpMethod.POST -> builder.post(generateBody(api)).url(httpUrl(api))
            ApiSpec.HttpMethod.PUT -> builder.put(generateBody(api)).url(httpUrl(api))
            ApiSpec.HttpMethod.DELETE -> builder.delete(generateBody(api)).url(httpUrl(api))
        }.build()
    }

    private fun httpUrlWithParameters(api: ApiSpec): HttpUrl {
        val urlBuilder = httpUrl(api).newBuilder()
        api.parameters.forEach { (key, value) ->
            urlBuilder.addQueryParameter(key, value)
        }
        return urlBuilder.build()
    }

    private fun httpUrl(api: ApiSpec): HttpUrl {
        return HttpUrl.parse(api.url) ?: throw RuntimeException("url is not available")
    }

    private fun headers(api: ApiSpec): Headers {
        val headerBuilder = Headers.Builder()
        api.headers.forEach { (key, value) ->
            headerBuilder.add(key, value)
        }
        return headerBuilder.build()
    }

    private fun generateBody(api: ApiSpec): RequestBody {
        return RequestBody.create(MediaType.parse(api.contentType.string), api.body)
    }

}