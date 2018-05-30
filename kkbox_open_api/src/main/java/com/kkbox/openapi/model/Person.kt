package com.kkbox.openapi.model

import java.io.Serializable

class Person(
        val id: String,
        val name: String,
        val description: String?,
        val avatars: List<ImageInfo>,
        val webUrl: String?
): Serializable