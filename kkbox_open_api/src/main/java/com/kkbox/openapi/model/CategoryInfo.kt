package com.kkbox.openapi.model

import java.io.Serializable

class CategoryInfo(
        val id: String,
        val title: String,
        val covers: List<ImageInfo>
) : Serializable