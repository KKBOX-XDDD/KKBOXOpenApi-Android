package com.kkbox.openapi.model

class ImageInfo(
        val type: Type,
        val url: String
) {
    enum class Type {
        SMALL,
        MEDIUM,
        BIG
    }
}