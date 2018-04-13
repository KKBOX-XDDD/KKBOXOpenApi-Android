package com.kkbox.openapi.model

class Track (
        val id:String,
        val name:String,
        val duration: Long,
        val albumIndex: Int,
        val explicitness: Boolean,
        val availableTerritories: List<Territory>,
        val album: AlbumInfo
)