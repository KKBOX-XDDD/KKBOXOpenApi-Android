package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.*
import me.showang.respect.core.HttpMethod
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.*

class PlaylistApi(private val playlistId: String) : OpenApiBase<PlaylistApi.ApiResult>() {

    override val url: String
        get() = "$baseUrl/shared-playlists/$playlistId"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET
    override val urlQueries: Map<String, String>         get() = super.urlQueries.toMutableMap().apply {
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

    fun offset(offset: Int): PlaylistApi {
        this.offset = offset
        return this
    }

    class ApiResult(
            val playlist: Playlist,
            val paging: Paging
    )



}