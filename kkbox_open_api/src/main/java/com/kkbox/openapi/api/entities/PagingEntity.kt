package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.model.Paging

data class PagingEntity (
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("previous") val previous: String?,
        @SerializedName("next") val next: String?
) {
    companion object {
        fun parse(json: PagingEntity): Paging {
            return Paging(
                    json.next != null,
                    json.offset.toString()
            )
        }
    }
}