package com.kkbox.openapi.model

import java.io.Serializable

class ImageInfo(
        val type: Type,
        val url: String
): Serializable {
    enum class Type {
        SMALL,
        MEDIUM,
        BIG
    }
}