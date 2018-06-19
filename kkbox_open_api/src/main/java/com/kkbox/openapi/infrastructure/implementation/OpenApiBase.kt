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

    protected val baseUrl: String get() = "https://api.kkbox.com/${Version.V1_1.string}"
    private var isLoading = false
    val isRequesting: Boolean
        get() = isLoading

    override val headers: Map<String, String>
        get() = ArrayMap<String, String>().apply {
            this["authorization"] = "Bearer $accessToken"
            this["accept"] = ApiSpec.ContentType.JSON.string
        }
    override val parameters: Map<String, String>
        get() = ArrayMap<String, String>().apply {
            this["territory"] = territory.name
        }
    override val contentType: ApiSpec.ContentType get() = ApiSpec.ContentType.JSON

    override val body: ByteArray get() = ByteArray(0)

    @Throws(Exception::class)
    protected abstract fun parse(result: ByteArray): ResultType

    open fun startRequest(failCallback: (Error) -> Unit = {}, successCallback: (ResultType) -> Unit) {
        if (requestExecutor == null) {
            throw RuntimeException("Request executor have to be initialized.")
        }
        isLoading = true
        requestExecutor?.request(this, failCallback, requestCompleteHandler(failCallback, successCallback))
    }

    private fun requestCompleteHandler(failCallback: (Error) -> Unit, successCallback: (ResultType) -> Unit): (ByteArray) -> Unit {
        return {
            asyncManager.start(
                    background = {
                        var error: Error? = null
                        var result: ResultType? = null
                        try {
                            result = parse(it)
                        } catch (e: Error) {
                            error = e
                        }
                        return@start ParseResult(error, result)
                    },
                    uiThread = {
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