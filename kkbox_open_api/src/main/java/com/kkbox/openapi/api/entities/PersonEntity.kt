package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.model.Person

/**
 * Represents users on KKBOX.
 */
data class PersonEntity(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String?,
        @SerializedName("images") val avatars: List<ImageEntity>?,
        @SerializedName("url") val url: String?
) {
  companion object {
    fun parse(json: PersonEntity): Person {
      return Person(
              json.id,
              json.name,
              json.description,
              ImageEntity.parse(json.avatars),
              json.url
      )
    }
  }
}