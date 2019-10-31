package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.AlbumEntity
import com.kkbox.openapi.api.parseTerritory
import com.kkbox.openapi.model.Territory
import com.kkbox.openapi.model.Track

data class RootTrackEntity(
        @SerializedName("tracks") val tracks: TracksEntity
)

/**
 * Represents tracks.
 */
data class TracksEntity(
        @SerializedName("data") val data: List<TrackEntity>,
        @SerializedName("paging") val paging: PagingEntity,
        @SerializedName("summary") val summary: SummaryEntity
)

/**
 * Represents tracks.
 */
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
                it.url,
                AlbumEntity.parse(it.album)
        )
      }
    }
  }
}
