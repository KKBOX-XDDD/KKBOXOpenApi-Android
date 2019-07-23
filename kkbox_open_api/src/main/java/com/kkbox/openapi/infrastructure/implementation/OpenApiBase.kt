package com.kkbox.openapi.infrastructure.implementation

import com.kkbox.openapi.KKBOXOpenApi
import com.kkbox.openapi.api.KKAuthApi
import com.kkbox.openapi.infrastructure.Crypto
import com.kkbox.openapi.model.Territory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.showang.respect.RespectApi
import me.showang.respect.core.ContentType
import me.showang.respect.start
import me.showang.respect.suspend
import java.lang.IllegalStateException

abstract class OpenApiBase<ResultType> : RespectApi<ResultType>() {

    enum class Version(val value: String) {
        V1_1("v1.1"),
    }

    companion object {
        var accessToken: String? = null
        var territory: Territory = Territory.TW
        var crypto: Crypto = AndroidCrypto()
    }

    val isRequesting: Boolean get() = isLoading
    private var isLoading = false
    protected val baseUrl: String by lazy { "https://api.kkbox.com/${Version.V1_1.value}" }
    override val headers: Map<String, String>
        get() = mutableMapOf<String, String>().apply {
            this["authorization"] = "Bearer $accessToken"
            this["accept"] = ContentType.JSON
        }

    override val urlQueries: Map<String, String>
        get() = mutableMapOf("territory" to territory.name)

    override val contentType get() = ContentType.JSON

    override val body: ByteArray get() = ByteArray(0)

    open fun startRequest(failCallback: (Throwable) -> Unit = {}, successCallback: (ResultType) -> Unit) {
        CoroutineScope(IO).launch {
            if (accessToken == null) {
                try {
                    accessToken = KKAuthApi(KKBOXOpenApi.clientId, KKBOXOpenApi.clientSecret).suspend().accessToken
                } catch (e: Throwable) {
                    withContext(Main) {
                        failCallback(IllegalStateException("Authorization problem"))
                    }
                }
            }
            start(failHandler = failCallback, successHandler = successCallback)
        }
    }

}