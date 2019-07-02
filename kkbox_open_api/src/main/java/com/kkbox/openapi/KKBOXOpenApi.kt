package com.kkbox.openapi

import com.kkbox.openapi.api.FeaturedPlaylistApi
import com.kkbox.openapi.api.KKAuthApi
import com.kkbox.openapi.infrastructure.Crypto
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Territory
import me.showang.respect.Respect
import me.showang.respect.core.RequestExecutor
import me.showang.respect.suspend

class KKBOXOpenApi {

    companion object {

        var clientId: String = ""
        var clientSecret: String = ""

        private var authToken: String = ""

        val isInstalled get() = clientId.isNotEmpty() && clientSecret.isNotEmpty()

        fun install(clientId: String, clientSecret: String, executor: RequestExecutor? = null) {
            Respect.requestExecutor = executor
            KKBOXOpenApi.clientId = clientId
            KKBOXOpenApi.clientSecret = clientSecret
        }

        suspend fun fetchAuthToken(failure: (Error) -> Unit = {}, success: () -> Unit = {}) {
            try {
                val authResult = KKAuthApi(clientId, clientSecret).suspend()
                authToken = authResult.accessToken
                OpenApiBase.accessToken = authResult.accessToken
                success()
            } catch (e: Error) {
                failure(e)
            }
        }

        fun fetchFeaturedPlaylist(failure: (Throwable) -> Unit, success: (FeaturedPlaylistApi.ApiResult) -> Unit) {
            FeaturedPlaylistApi().startRequest(failure, success)
        }

        fun update(crypto: Crypto) {
            OpenApiBase.crypto = crypto
        }

        fun update(territory: Territory) {
            OpenApiBase.territory = territory
        }

    }


}