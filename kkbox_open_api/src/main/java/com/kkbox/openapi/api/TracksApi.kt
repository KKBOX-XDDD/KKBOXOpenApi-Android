package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.SummaryEntity
import com.kkbox.openapi.infrastructure.ApiSpec
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Paging
import com.kkbox.openapi.model.Territory
import com.kkbox.openapi.model.Track

class TracksApi(private val playlistId: String, private var offset: Int? = null) : OpenApiBase<TracksApi.ApiResult>() {

    override val url: String
        get() = "${super.baseUrl}/shared-playlists/$playlistId/tracks"
    override val httpMethod: ApiSpec.HttpMethod
        get() = ApiSpec.HttpMethod.GET
    override val parameters: Map<String, String>
        get() = super.parameters.toMutableMap().apply {
            if (offset != null) this["offset"] = offset.toString()
        }

    override fun parse(result: ByteArray): ApiResult {
        val jsonString = String(result)
        val rootEntity: RootEntity = Gson().fromJson(jsonString, RootEntity::class.java)
        return ApiResult(
                rootEntity.data.map {
                    Track(
                            it.id,
                            it.name,
                            it.duration,
                            it.track_number,
                            it.explicitness,
                            it.availableTerritories.map { Territory.valueOf(it) },
                            it.url,
                            PlaylistApi.AlbumEntity.parse(it.album)
                    )
                },
                PagingEntity.parse(rootEntity.data.size, rootEntity.paging, rootEntity.summary)
        )
    }

    fun offset(index: Int): TracksApi {
        this.offset = index
        return this
    }

    class ApiResult(
            val tracks: List<Track>,
            val paging: Paging
    )

    private data class RootEntity(
            @SerializedName("data") val data: List<PlaylistApi.TrackEntity>,
            @SerializedName("paging") val paging: PagingEntity,
            @SerializedName("summary") val summary: SummaryEntity
    )

}