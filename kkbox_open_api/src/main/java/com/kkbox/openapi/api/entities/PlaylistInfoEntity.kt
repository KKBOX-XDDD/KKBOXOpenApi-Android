package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.model.PlaylistInfo
import java.text.SimpleDateFormat
import java.util.*

/**
 * Represents metdata of a playlist.
 */
data class PlaylistInfoEntity(
        @SerializedName("id") val id: String,
        @SerializedName("title") val title: String,
        @SerializedName("description") val description: String,
        @SerializedName("url") val url: String,
        @SerializedName("updated_at") val updateDescription: String,
        @SerializedName("images") val coverImages: List<ImageEntity>,
        @SerializedName("owner") val owner: PersonEntity
) {
    companion object {
        fun parse(json: PlaylistInfoEntity): PlaylistInfo {//2018-05-31T04:09:21+00:00
            val sourceDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            val date = sourceDateFormat.parse(json.updateDescription)
            return PlaylistInfo(
                    json.id,
                    json.title,
                    json.description,
                    date.time,
                    PersonEntity.parse(json.owner),
                    ImageEntity.parse(json.coverImages),
                    json.url
            )
        }
    }
}