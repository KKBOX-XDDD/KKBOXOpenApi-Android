package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.model.ImageInfo

/**
 * Represents images.
 */
data class ImageEntity(
        @SerializedName("height") val height: Int,
        @SerializedName("width") val width: Int,
        @SerializedName("url") val url: String
) {
    companion object {
        fun parse(jsonList: List<ImageEntity>?): List<ImageInfo> {
            return jsonList?.mapIndexed { index, imageEntity ->
                when (index) {
                    0 -> ImageInfo(ImageInfo.Type.SMALL, imageEntity.url)
                    1 -> ImageInfo(ImageInfo.Type.MEDIUM, imageEntity.url)
                    else -> ImageInfo(ImageInfo.Type.BIG, imageEntity.url)
                }
            } ?: ArrayList(0)
        }
    }
}