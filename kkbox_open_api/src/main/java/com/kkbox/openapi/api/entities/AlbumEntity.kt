package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.parseTerritory
import com.kkbox.openapi.model.AlbumInfo

/**
 * Represents albums.
 */
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
              parseTerritory(json.availableTerritories),
              json.release_date,
              ImageEntity.parse(json.images),
              PersonEntity.parse(json.artist)
      )
    }
  }
}
