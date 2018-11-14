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

class SearchPlaylistApi(private val keyWords: String?) : OpenApiBase<SearchPlaylistApi.ApiResult>() {


    override fun parse(bytes: ByteArray): ApiResult {
        val json = Gson().fromJson(String(bytes), RootEntity::class.java)
        return ApiResult(
                json.playlists.playlistInfoList.map {
                    PlaylistInfoEntity.parse(it)
                },
                PagingEntity.parse(json.playlists.playlistInfoList.size, json.playlists.paging, json.playlists.summary)
        )
    }

    private var offset: Int? = null

    fun offset(offset: Int): SearchPlaylistApi {
        this.offset = offset
        return this
    }

    override val url: String
        get() = "$baseUrl/search"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET
    override val urlQueries: Map<String, String>         get() = super.urlQueries.toMutableMap().apply {
            if (keyWords != null) this["q"] = keyWords
            if (offset != null) this["offset"] = offset.toString()
            this["type"] = "playlist"
        }

    class ApiResult(
            val playlistInfoList: List<PlaylistInfo>,
            val paging: Paging
    )

    private data class RootEntity(
            @SerializedName("playlists") val playlists: PlaylistsEntity
    )

    private data class PlaylistsEntity(
            @SerializedName("data") val playlistInfoList: List<PlaylistInfoEntity>,
            @SerializedName("paging") val paging: PagingEntity,
            @SerializedName("summary") val summary: SummaryEntity
    )
}