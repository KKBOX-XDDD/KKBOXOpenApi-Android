package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName

/**
 * Represents summary of a list.
 */
data class SummaryEntity(
        @SerializedName("total") val totalCount: Int
)