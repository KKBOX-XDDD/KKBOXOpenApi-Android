package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.PlaylistInfoEntity
import com.kkbox.openapi.api.entities.SummaryEntity
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Paging
import com.kkbox.openapi.model.PlaylistInfo
import me.showang.respect.core.HttpMethod

/**
 * Fetches charts.
 *
 * See https://docs-zhtw.kkbox.codes/reference#charts
 */
class ChartsApi : OpenApiBase<ChartsApi.ApiResult>() {

    override fun parse(bytes: ByteArray): ApiResult {
        val json = Gson().fromJson(String(bytes), RootEntity::class.java)
        return ApiResult(
                json.data.map { PlaylistInfoEntity.parse(it) },
                PagingEntity.parse(json.data.size, json.paging, json.summary)
        )
    }

    override val url: String
        get() = "$baseUrl/charts"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET

    private data class RootEntity(
            @SerializedName("data") val data: List<PlaylistInfoEntity>,
            @SerializedName("paging") val paging: PagingEntity,
            @SerializedName("summary") val summary: SummaryEntity
    )

    /**
     * The result of ChartsApi.
     *
     * @property playlistList a list of playlist.
     * @property paging the paging information.
     */
    class ApiResult(
            val playlistList: List<PlaylistInfo>,
            val paging: Paging
    )

}
