package com.kkbox.openapi.api

import android.support.v4.util.ArrayMap
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.*
import com.kkbox.openapi.infrastructure.ApiSpec
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.*

class PlaylistApi(private val playlistId: String) : OpenApiBase<PlaylistApi.ApiResult>() {

    override val url: String
        get() = "$baseUrl/shared-playlists/$playlistId"
    override val httpMethod: ApiSpec.HttpMethod
        get() = ApiSpec.HttpMethod.GET
    override val parameters: Map<String, String>
        get() {
            val parameters = ArrayMap<String, String>()
            parameters.putAll(super.parameters)
            if (offset != null) parameters["offset"] = offset.toString()
            return parameters
        }

    private var offset: Int? = null

    override fun parse(result: ByteArray): ApiResult {
        val gson = Gson()
        val json = String(result)
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

    fun offset(offset: Int): PlaylistApi{
        this.offset = offset
        return this
    }

    class ApiResult(
            val playlist: Playlist,
            val paging: Paging
    )

    private data class RootTrackEntity(
            @SerializedName("tracks") val tracks: TracksEntity
    )

    data class TracksEntity(
            @SerializedName("data") val data: List<TrackEntity>,
            @SerializedName("paging") val paging: PagingEntity,
            @SerializedName("summary") val summary: SummaryEntity
    )

    data class TrackEntity(
            @SerializedName("id") val id: String,
            @SerializedName("name") val name: String,
            @SerializedName("duration") val duration: Long,
            @SerializedName("url") val url: String,
            @SerializedName("track_number") val track_number: Int,
            @SerializedName("explicitness") val explicitness: Boolean,
            @SerializedName("available_territories") val availableTerritories: List<String>,
            @SerializedName("album") val album: AlbumEntity
    ) {

        companion object {
            fun parse(json: List<TrackEntity>): List<Track> {
                return json.map {
                    Track(
                            it.id,
                            it.name,
                            it.duration,
                            it.track_number,
                            it.explicitness,
                            parseTerritory(it.availableTerritories),
                            AlbumEntity.parse(it.album)
                    )
                }
            }

            fun parseTerritory(territories: List<String>): List<Territory> {
                return territories.map {
                    when (it) {
                        "JP" -> Territory.JP
                        "HK" -> Territory.HK
                        "MY" -> Territory.MY
                        "SG" -> Territory.SG
                        else -> Territory.TW
                    }
                }
            }
        }
    }

    data class AlbumEntity(
            @SerializedName("id") val id: String,
            @SerializedName("name") val name: String,
            @SerializedName("url") val url: String,
            @SerializedName("explicitness") val explicitness: Boolean,
            @SerializedName("available_territories") val availableTerritories: List<String>,
            @SerializedName("release_date") val release_date: String?,
            @SerializedName("images") val images: List<ImageEntity>,
            @SerializedName("artist") val artist: PersonEntity
    ) {
        companion object {
            fun parse(json: AlbumEntity): AlbumInfo {
                return AlbumInfo(
                        json.id,
                        json.name,
                        json.url,
                        json.explicitness,
                        TrackEntity.parseTerritory(json.availableTerritories),
                        json.release_date,
                        ImageEntity.parse(json.images),
                        PersonEntity.parse(json.artist)
                )
            }
        }
    }
}