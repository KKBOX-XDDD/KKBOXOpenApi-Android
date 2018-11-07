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

class CategoryPlaylistsApi(private val categoryId: String) : OpenApiBase<CategoryPlaylistsApi.ApiResult>() {

    private var offset: Int? = null

    fun offset(offset: Int): CategoryPlaylistsApi {
        this.offset = offset
        return this
    }

    override val url: String
        get() = "$baseUrl/featured-playlist-categories/$categoryId/playlists"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET
    override val urlQueries: Map<String, String>         get() = super.urlQueries.toMutableMap().apply {
            if (offset != null) this["offset"] = offset!!.toString()
        }


    override fun parse(result: ByteArray): CategoryPlaylistsApi.ApiResult {
        val json = Gson().fromJson(String(result), CategoryPlaylistsApi.RootEntity::class.java)
        return CategoryPlaylistsApi.ApiResult(
                json.data.map { PlaylistInfoEntity.parse(it) },
                PagingEntity.parse(json.data.size, json.pagingInfo, json.summary)
        )
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