package com.kkbox.openapi

import com.kkbox.openapi.api.FeaturedPlaylistApi
import com.kkbox.openapi.api.KKAuthApi
import com.kkbox.openapi.infrastructure.Crypto
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Territory
import me.showang.respect.Respect
import me.showang.respect.core.RequestExecutor
import me.showang.respect.okhttp.OkhttpRequestExecutor
import okhttp3.OkHttpClient

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

        fun fetchAuthToken(failure: (Error) -> Unit = {}, success: () -> Unit = {}) {
            KKAuthApi(clientId, clientSecret).startRequest(failure) {
                authToken = it.accessToken
                OpenApiBase.accessToken = it.accessToken
                success()
            }
        }

        fun fetchFeaturedPlaylist(failure: (Error) -> Unit, success: (FeaturedPlaylistApi.ApiResult) -> Unit) {
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