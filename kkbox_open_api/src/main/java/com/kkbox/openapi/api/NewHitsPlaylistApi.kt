package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.PlaylistInfoEntity
import com.kkbox.openapi.api.entities.SummaryEntity
import me.showang.respect.core.HttpMethod
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Paging
import com.kkbox.openapi.model.PlaylistInfo

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

    override fun parse(bytes: ByteArray): ApiResult {
        val json = Gson().fromJson(String(bytes), NewHitsPlaylistApi.RootEntity::class.java)
        return NewHitsPlaylistApi.ApiResult(
                json.data.map { PlaylistInfoEntity.parse(it) },
                PagingEntity.parse(json.data.size, json.pagingInfo, json.summary)
        )
    }

    override val url: String
        get() = "$baseUrl/new-hits-playlists"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET
    override val urlQueries: Map<String, String>         get() = super.urlQueries.toMutableMap().apply {
            if (offset != null) this["offset"] = offset!!
            if (limit != null) this["limit"] = limit.toString()
        }

    class ApiResult(
            val playlistList: List<PlaylistInfo>,
            val paging: Paging
    )

    private data class RootEntity(
            @SerializedName("data") val data: List<PlaylistInfoEntity>,
            @SerializedName("paging") val pagingInfo: PagingEntity,
            @SerializedName("summary") val summary: SummaryEntity
    )
}