package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.AlbumEntity
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.SummaryEntity
import com.kkbox.openapi.api.entities.TrackEntity
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Paging
import com.kkbox.openapi.model.Territory
import com.kkbox.openapi.model.Track
import me.showang.respect.core.HttpMethod

/**
 * Fetches tracks in a playlist.
 *
 * See https://docs-zhtw.kkbox.codes/reference#shared-playlists_playlist_id_tracks
 */
class PlaylistTracksApi(private val playlistId: String, private var offset: Int? = null) :
        OpenApiBase<PlaylistTracksApi.ApiResult>() {

    override val httpMethod = HttpMethod.GET
    override val url: String by lazy { "${super.baseUrl}/shared-playlists/$playlistId/tracks" }
    override val urlQueries: Map<String, String> by lazy {
        super.urlQueries.toMutableMap().apply {
            offset?.let { set("offset", it.toString()) }
        }
    }

    override fun parse(bytes: ByteArray): ApiResult {
        val jsonString = String(bytes)
        val rootEntity: RootEntity = Gson().fromJson(jsonString, RootEntity::class.java)
        return ApiResult(
                rootEntity.data.map {
                    Track(
                            it.id,
                            it.name,
                            it.duration,
                            it.track_number,
                            it.explicitness,
                            it.availableTerritories.map { availableTerritories -> Territory.valueOf(availableTerritories) },
                            it.url,
                            it.album?.let { album -> AlbumEntity.parse(album) }

                    )
                },
                PagingEntity.parse(rootEntity.data.size, rootEntity.paging, rootEntity.summary)
        )
    }

    /**
     * Sets the expected offset of the fetched list.
     *
     * @param offset the offset.
     * @return an instance of NewHitsPlaylistApi.
     */
    fun offset(index: Int): PlaylistTracksApi {
        this.offset = index
        return this
    }

    class ApiResult(
            val tracks: List<Track>,
            val paging: Paging
    )

    private data class RootEntity(
            @SerializedName("data") val data: List<TrackEntity>,
            @SerializedName("paging") val paging: PagingEntity,
            @SerializedName("summary") val summary: SummaryEntity
    )

}