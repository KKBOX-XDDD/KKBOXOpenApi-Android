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
 * Fetches featured playlists.
 *
 * See https://docs-zhtw.kkbox.codes/reference#featured-playlists
 */
class FeaturedPlaylistApi : OpenApiBase<FeaturedPlaylistApi.ApiResult>() {

    private var offset: String? = null
    private var limit: Int? = null

    fun page(offset: String): FeaturedPlaylistApi {
        this.offset = offset
        return this
    }

    fun item(limit: Int): FeaturedPlaylistApi {
        this.limit = limit
        return this
    }

    override fun parse(bytes: ByteArray): ApiResult {
        val json = Gson().fromJson(String(bytes), RootEntity::class.java)
        return ApiResult(
                json.data.map { PlaylistInfoEntity.parse(it) },
                PagingEntity.parse(json.data.size, json.pagingInfo, json.summary)
        )
    }

    override val url: String
        get() = "$baseUrl/featured-playlists"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET
    override val urlQueries: Map<String, String>
        get() = super.urlQueries.toMutableMap().apply {
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

