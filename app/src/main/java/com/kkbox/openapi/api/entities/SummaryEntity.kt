package com.kkbox.openapi.api.entities

import com.google.gson.annotations.SerializedName

data class SummaryEntity (
        @SerializedName("total") val totalCount: Int
)