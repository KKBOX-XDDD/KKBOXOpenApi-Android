package com.kkbox.openapi.model

import java.io.Serializable

class AlbumInfo(
        val id: String,
        val name: String,
        val webUrl: String,
        val explicitness: Boolean,
        val availableTerritories: List<Territory>,
        val releaseDate: String?,
        val covers: List<ImageInfo>,
        val artist: Person
): Serializable