package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.model.Paging

/**
 * Represents paging.
 */
data class PagingEntity(
        @SerializedName("offset") val offset: Int,
        @SerializedName("limit") val limit: Int,
        @SerializedName("previous") val previous: String?,
        @SerializedName("next") val next: String?
) {
  companion object {
    fun parse(currentItemCount: Int, paging: PagingEntity, summary: SummaryEntity? = null): Paging {
      val nextOffset = currentItemCount + paging.offset
      return Paging(
              paging.next != null,
              nextOffset
      )
    }
  }
}