package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.PlaylistInfoEntity
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.SummaryEntity
import com.kkbox.openapi.infrastructure.ApiSpec
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.PlaylistInfo
import com.kkbox.openapi.model.Paging

class ChartsApi:OpenApiBase<ChartsApi.ApiResult>() {

    override fun parse(result: ByteArray): ApiResult {
        val json = Gson().fromJson(String(result), RootEntity::class.java)
        return ApiResult(
                json.data.map { PlaylistInfoEntity.parse(it) },
                PagingEntity.parse(json.data.size, json.paging, json.summary)
        )
    }

    override val url: String
        get() = "$baseUrl/charts"
    override val httpMethod: ApiSpec.HttpMethod
        get() = ApiSpec.HttpMethod.GET

    class ApiResult(
            val playlistList: List<PlaylistInfo>,
            val paging: Paging
    )

    private data class RootEntity (
            @SerializedName("data") val data:List<PlaylistInfoEntity>,
            @SerializedName("paging") val paging: PagingEntity,
            @SerializedName("summary") val summary: SummaryEntity
    )

}