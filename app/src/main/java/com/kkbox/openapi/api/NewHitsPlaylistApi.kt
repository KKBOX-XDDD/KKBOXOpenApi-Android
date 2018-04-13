package com.kkbox.openapi.api

import android.support.v4.util.ArrayMap
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.api.entities.PlaylistInfoEntity
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.infrastructure.ApiSpec
import com.kkbox.openapi.model.PlaylistInfo
import com.kkbox.openapi.model.Paging

class NewHitsPlaylistApi : OpenApiBase<NewHitsPlaylistApi.ApiResult>() {

    private var offset: String? = null
    private var limit: Int? = null

    fun page(offset: String): NewHitsPlaylistApi {
        this.offset = offset
        return this
    }

    fun item(limit: Int): NewHitsPlaylistApi {
        this.limit = limit
        return this
    }

    override fun parse(result: ByteArray): ApiResult {
        val json = Gson().fromJson(String(result), NewHitsPlaylistApi.RootEntity::class.java)
        return NewHitsPlaylistApi.ApiResult(
                json.data.map { PlaylistInfoEntity.parse(it) },
                PagingEntity.parse(json.pagingInfo)
        )
    }

    override val url: String
        get() = "$baseUrl/new-hits-playlists"
    override val httpMethod: ApiSpec.HttpMethod
        get() = ApiSpec.HttpMethod.GET
    override val parameters: Map<String, String>
        get() {
            val parameters = ArrayMap<String, String>()
            parameters.putAll(super.parameters)
            if (offset != null) parameters["offset"] = offset
            if (limit != null) parameters["limit"] = limit.toString()
            return parameters
        }

    class ApiResult(
            val playlistList: List<PlaylistInfo>,
            val paging: Paging
    )

    private data class RootEntity (
            @SerializedName("data") val data:List<PlaylistInfoEntity>,
            @SerializedName("paging") val pagingInfo: PagingEntity,
            @SerializedName("summary") val summary: SummaryEntity
    )

    private data class SummaryEntity (
            @SerializedName("total") val totalCount: Int
    )
}