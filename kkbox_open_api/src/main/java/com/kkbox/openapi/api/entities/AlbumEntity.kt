package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.parseTerritory
import com.kkbox.openapi.model.AlbumInfo

/**
 * Represents albums.
 *
 * @property id the ID of the album.
 * @property name the name of the album.
 * @property url the URL of the webpage for the album.
 * @property explicitness if the album is explicit or not.
 * @property availableTerritories the territories where the album is available.
 * @property releaseDate when was the album released.
 * @property images the cover images in various resolutions for the album.
 * @property artist the artist who creates the album.
 */
data class AlbumEntity(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String,
        @SerializedName("explicitness") val explicitness: Boolean,
        @SerializedName("available_territories") val availableTerritories: List<String>,
        @SerializedName("release_date") val releaseDate: String?,
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
              json.releaseDate,
              ImageEntity.parse(json.images),
              PersonEntity.parse(json.artist)
      )
    }
  }
}
