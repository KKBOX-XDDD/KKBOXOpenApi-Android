package com.kkbox.openapi.api

import android.support.v4.util.ArrayMap
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.PlaylistInfoEntity
import com.kkbox.openapi.api.entities.SummaryEntity
import com.kkbox.openapi.infrastructure.ApiSpec
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Paging
import com.kkbox.openapi.model.PlaylistInfo
import java.net.URLEncoder

class SearchPlaylistApi(private val keyWords: String?) : OpenApiBase<SearchPlaylistApi.ApiResult>() {


    override fun parse(result: ByteArray): ApiResult {
        val json = Gson().fromJson(String(result), RootEntity::class.java)
        return ApiResult(
                json.playlists.playlistInfoList.map {
                    PlaylistInfoEntity.parse(it)
                },
                PagingEntity.parse(json.playlists.playlistInfoList.size, json.playlists.paging, json.playlists.summary)
        )
    }

    override val url: String
        get() = "$baseUrl/search"
    override val httpMethod: ApiSpec.HttpMethod
        get() = ApiSpec.HttpMethod.GET
    override val parameters: Map<String, String>
        get() {
            val parameters = ArrayMap<String, String>()
            parameters.putAll(super.parameters)
            if (keyWords != null) {
                parameters["q"] = URLEncoder.encode(keyWords, "UTF-8")
            }
            parameters["type"] = "playlist"
            return parameters
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