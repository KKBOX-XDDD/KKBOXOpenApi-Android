package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.infrastructure.ApiSpec
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import java.nio.charset.Charset


class KKAuthApi(
        private val clientId: String,
        private val clientSecret: String
) : OpenApiBase<KKAuthApi.AuthResult>() {

    override val url: String get() = "https://account.kkbox.com/oauth2/token"
    override val httpMethod: ApiSpec.HttpMethod get() = ApiSpec.HttpMethod.POST
    override val body: ByteArray get() = "grant_type=client_credentials".toByteArray()
    override val contentType: ApiSpec.ContentType get() = ApiSpec.ContentType.FORM_URL_ENCODED
    override val headers: Map<String, String>
        get() = mutableMapOf<String, String>().apply {
            this["Authorization"] = "Basic ${base64("$clientId:$clientSecret")}"
        }

    private fun base64(input: String): String {
        return OpenApiBase.crypto.base64(input)
    }

    override fun parse(result: ByteArray): AuthResult {
        return Gson().fromJson(result.toString(Charset.defaultCharset()), AuthResult::class.java)
    }


    data class AuthResult(
            @SerializedName("access_token") val accessToken: String,
            @SerializedName("expires_in") val expire: Long
    )
}