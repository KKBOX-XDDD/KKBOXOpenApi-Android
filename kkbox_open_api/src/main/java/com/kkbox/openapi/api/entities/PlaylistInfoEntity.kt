package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.model.PlaylistInfo

data class PlaylistInfoEntity (
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("url") val url: String,
        @SerializedName("updated_at") val updateDescription: String,
        @SerializedName("images") val coverImages: List<ImageEntity>,
        @SerializedName("owner") val owner: PersonEntity
) {
    companion object {
        fun parse(json: PlaylistInfoEntity): PlaylistInfo {
            return PlaylistInfo(
                    json.id,
                    json.title,
                    json.description,
                    json.updateDescription,
                    PersonEntity.parse(json.owner),
                    ImageEntity.parse(json.coverImages),
                    json.url
            )
        }
    }
}