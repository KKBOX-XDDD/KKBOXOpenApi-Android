package com.kkbox.openapi

import com.kkbox.openapi.api.FeaturedPlaylistApi
import com.kkbox.openapi.infrastructure.*
import com.kkbox.openapi.api.KKAuthApi
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.infrastructure.implementation.AndroidAsyncManager
import com.kkbox.openapi.infrastructure.implementation.OkhttpRequestExecutor
import com.kkbox.openapi.model.Territory
import okhttp3.OkHttpClient

class KKBOXOpenApi {

    companion object {

        private var clientId: String = ""
        private var clientSecret: String = ""

        private var authToken: String = ""

        val isInstalled get() = OpenApiBase.requestExecutor != null

        fun install(clientId: String, clientSecret: String, executor: RequestExecutor = OkhttpRequestExecutor(OkHttpClient()), asyncManager: AsyncManager = AndroidAsyncManager()) {
            OpenApiBase.requestExecutor = executor
            OpenApiBase.asyncManager = asyncManager
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