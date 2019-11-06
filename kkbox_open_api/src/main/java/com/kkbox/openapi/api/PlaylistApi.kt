package com.kkbox.openapi.api

import com.google.gson.Gson
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.PlaylistInfoEntity
import com.kkbox.openapi.api.entities.RootTrackEntity
import com.kkbox.openapi.api.entities.TrackEntity
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Paging
import com.kkbox.openapi.model.Playlist
import me.showang.respect.core.HttpMethod

/**
 * Fetches playlists.
 *
 * Please note that the API may not contain all of the tracks of the playlist. To fetch
 * the tracks not contained in the API, you may want to use `PlaylistTracksApi`.
 *
 * See https://docs-zhtw.kkbox.codes/v1.1/reference#shared-playlists_playlist_id
 *
 * @property playlistId the ID of the desired playlist.
 */
class PlaylistApi(private val playlistId: String) : OpenApiBase<PlaylistApi.ApiResult>() {

    override val url: String
        get() = "$baseUrl/shared-playlists/$playlistId"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET
    override val urlQueries: Map<String, String>
        get() = super.urlQueries.toMutableMap().apply {
            if (offset != null) this["offset"] = offset.toString()
        }

    private var offset: Int? = null

    override fun parse(bytes: ByteArray): ApiResult {
        val gson = Gson()
        val json = String(bytes)
        val playlistJson = gson.fromJson(json, PlaylistInfoEntity::class.java)
        val tracksJson = gson.fromJson(json, RootTrackEntity::class.java)
        val tracks = TrackEntity.parse(tracksJson.tracks.data)
        return ApiResult(
                Playlist(
                        PlaylistInfoEntity.parse(playlistJson),
                        tracks
                ),
                PagingEntity.parse(tracks.size, tracksJson.tracks.paging, tracksJson.tracks.summary)
        )
    }

    /**
     * Sets the expected offset of the fetched list.
     *
     * @param offset the offset.
     * @return an instance of NewHitsPlaylistApi.
     */
    fun offset(offset: Int): PlaylistApi {
        this.offset = offset
        return this
    }

    /**
     * The result of PlaylistApi.
     *
     * @property playlist the fetched playlist.
     * @property paging the paging information.
     */
    class ApiResult(
            val playlist: Playlist,
            val paging: Paging
    )
}
