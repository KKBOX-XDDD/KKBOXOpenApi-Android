package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import me.showang.respect.RespectApi
import me.showang.respect.core.ContentType
import me.showang.respect.core.HttpMethod
import java.nio.charset.Charset


class KKAuthApi(
        private val clientId: String,
        private val clientSecret: String
) : OpenApiBase<KKAuthApi.AuthResult>() {

    override val url: String get() = "https://account.kkbox.com/oauth2/token"
    override val httpMethod get() = HttpMethod.POST
    override val body: ByteArray get() = "grant_type=client_credentials".toByteArray()
    override val contentType get() = ContentType.FORM_URL_ENCODED
    override val headers: Map<String, String>
        get() = mutableMapOf<String, String>().apply {
            this["Authorization"] = "Basic ${base64("$clientId:$clientSecret")}"
        }

    private fun base64(input: String): String {
        return OpenApiBase.crypto.base64(input)
    }

    override fun parse(bytes: ByteArray): AuthResult {
        return Gson().fromJson(bytes.toString(Charset.defaultCharset()), AuthResult::class.java)
    }

    data class AuthResult(
            @SerializedName("access_token") val accessToken: String,
            @SerializedName("expires_in") val expire: Long
    )
}