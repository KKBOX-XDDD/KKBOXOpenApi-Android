package com.kkbox.openapi.infrastructure

interface ApiSpec {

    val url: String
    val httpMethod: HttpMethod
    val contentType: ContentType

    val headers: Map<String, String>
    val parameters: Map<String, String>

    val body: ByteArray

    val priority: Priority get() = Priority.NORMAL
    val timeout: Long get() = 3000

    enum class HttpMethod {
        GET, POST, PUT, DELETE
    }

    enum class ContentType(val string: String) {
        JSON("application/json"),
        FORM_URL_ENCODED("application/x-www-form-urlencoded")
    }

    enum class Priority {
        NORMAL, LOW, HIGH, IMMEDIATE
    }
}