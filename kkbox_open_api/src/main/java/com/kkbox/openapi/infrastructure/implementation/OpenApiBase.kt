package com.kkbox.openapi.infrastructure.implementation

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
    val isRequesting: Boolean get() = isLoading

    override val headers: Map<String, String>
        get() = mutableMapOf<String, String>().apply {
            this["authorization"] = "Bearer $accessToken"
            this["accept"] = ApiSpec.ContentType.JSON.string
        }
    override val parameters: Map<String, String>
        get() = mutableMapOf<String, String>().apply {
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
        return { bytes ->
            asyncManager.start(
                    background = {
                        var error: Error? = null
                        var result: ResultType? = null
                        try {
                            result = parse(bytes)
                        } catch (e: Error) {
                            error = e
                        }
                        return@start ParsedResult(error, result)
                    },
                    uiThread = {
                        isLoading = false
                        it.error?.let(failCallback) ?: run {
                            it.apiResult?.let(successCallback)
                                    ?: failCallback(Error("Server Error(Result Empty)"))
                        }
                    }
            )
        }
    }

    private fun emptyMap(): Map<String, String> {
        return mapOf()
    }

    private class ParsedResult<out ResultType>(val error: Error?, val apiResult: ResultType?)
}