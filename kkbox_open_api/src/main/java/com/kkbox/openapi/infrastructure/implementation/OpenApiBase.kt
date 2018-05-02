package com.kkbox.openapi.infrastructure.implementation

import android.support.v4.util.ArrayMap
import com.kkbox.openapi.infrastructure.ApiSpec
import com.kkbox.openapi.infrastructure.AsyncManager
import com.kkbox.openapi.infrastructure.Crypto
import com.kkbox.openapi.infrastructure.RequestExecutor
import com.kkbox.openapi.model.Territory

abstract class OpenApiBase<out ResultType> : ApiSpec {

    enum class Version(val string: String) {
        V1_1("v1.1"),
    }

    companion object {
        var requestExecutor: RequestExecutor? = null
        var asyncManager: AsyncManager = AndroidAsyncManager()
        var accessToken: String = ""
        var territory: Territory = Territory.TW
        var crypto: Crypto = AndroidCrypto()
    }

    protected val baseUrl: String
        get() {
            return "https://api.kkbox.com/${Version.V1_1.string}"
        }
    private var isLoading = false
    val isRequesting: Boolean
        get() = isLoading

    override val headers: Map<String, String>
        get() {
            val headers = ArrayMap<String, String>()
            headers["authorization"] = "Bearer $accessToken"
            headers["accept"] = ApiSpec.ContentType.JSON.string
            return headers
        }
    override val parameters: Map<String, String>
        get() {
            val parameters = ArrayMap<String, String>()
            parameters["territory"] = territory.name
            return parameters
        }
    override val contentType: ApiSpec.ContentType
        get() {
            return ApiSpec.ContentType.JSON
        }
    override val body: ByteArray get() = ByteArray(0)

    @Throws(Exception::class)
    protected abstract fun parse(result: ByteArray): ResultType

    open fun startRequest(failCallback: (Error) -> Unit, successCallback: (ResultType) -> Unit) {
        if (requestExecutor == null) {
            throw RuntimeException("Request executor have to be initialized.")
        }
        isLoading = true
        requestExecutor?.request(this, failCallback, requestCompleteHandler(failCallback, successCallback))
    }

    private fun requestCompleteHandler(failCallback: (Error) -> Unit, successCallback: (ResultType) -> Unit): (ByteArray) -> Unit {
        return {
            asyncManager.start(
                    {
                        var error: Error? = null
                        var result: ResultType? = null
                        try {
                            result = parse(it)
                        } catch (e: Error) {
                            error = e
                        }
                        return@start ParseResult(error, result)
                    },
                    {
                        isLoading = false
                        if (it.error == null) {
                            if (it.apiResult != null) {
                                successCallback(it.apiResult)
                            } else {
                                failCallback(Error("Server Error(Result Empty)"))
                            }
                        } else {
                            failCallback(it.error)
                        }
                    }
            )
        }
    }

    private fun emptyMap(): Map<String, String> {
        return ArrayMap<String, String>(0)
    }

    private class ParseResult<out ResultType>(val error: Error?, val apiResult: ResultType?)
}