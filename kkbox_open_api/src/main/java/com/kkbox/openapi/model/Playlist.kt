package com.kkbox.openapi.model

class Playlist(
        val info: PlaylistInfo,
        val tracks: List<Track>,
        val totalTrackCount: Int
) {

    fun merge(playlist: Playlist) {

    }

}