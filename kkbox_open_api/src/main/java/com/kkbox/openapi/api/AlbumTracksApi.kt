package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.SummaryEntity
import com.kkbox.openapi.api.entities.TrackEntity
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Paging
import com.kkbox.openapi.model.Territory
import com.kkbox.openapi.model.Track
import me.showang.respect.core.HttpMethod

/**
 * Fetches tracks in an album.
 *
 * See https://docs-zhtw.kkbox.codes/reference#albums_album_id_tracks
 */
class AlbumTracksApi(private val albumId: String, private var offset: Int? = null) :
        OpenApiBase<AlbumTracksApi.ApiResult>() {

  override val httpMethod = HttpMethod.GET
  override val url: String by lazy { "${super.baseUrl}/albums/$albumId/tracks" }
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
                      it.availableTerritories.map { Territory.valueOf(it) },
                      it.url,
                      it.album?.let { album -> AlbumEntity.parse(album) }
              )
            },
            PagingEntity.parse(rootEntity.data.size, rootEntity.paging, rootEntity.summary)
    )
  }

  fun offset(index: Int): AlbumTracksApi {
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