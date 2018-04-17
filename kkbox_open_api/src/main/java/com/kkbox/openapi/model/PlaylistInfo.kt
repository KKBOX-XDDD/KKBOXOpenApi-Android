package com.kkbox.openapi.model

import java.io.Serializable

class PlaylistInfo(
        val id: String,
        val title: String,
        val description: String,
        val updateDescription: String,
        val owner: Person,
        val covers: List<ImageInfo>,
        val webUrl: String
): Serializable