package com.kkbox.openapi.model

import java.io.Serializable

class Track (
        val id:String,
        val name:String,
        val duration: Long,
        val albumIndex: Int,
        val explicitness: Boolean,
        val availableTerritories: List<Territory>,
        val webUrl: String,
        val album: AlbumInfo
): Serializable {

    var currentPlaylist: Playlist? = null

}