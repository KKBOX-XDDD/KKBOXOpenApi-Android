package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.PlaylistInfoEntity
import com.kkbox.openapi.infrastructure.ApiSpec
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Paging
import com.kkbox.openapi.model.PlaylistInfo
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

class UserSharedPlaylistApi(private val userId: String, private val offset: Int? = 0) : OpenApiBase<UserSharedPlaylistApi.ApiResult>() {
    override fun parse(result: ByteArray): ApiResult {
        val jsonEntity: RootEntity = Gson().fromJson(JsonReader(InputStreamReader(ByteArrayInputStream(result))), RootEntity::class.java)
        return ApiResult(
                jsonEntity.data.map { PlaylistInfoEntity.parse(it) },
                PagingEntity.parse(jsonEntity.data.size, jsonEntity.paging)
        )
    }

    override val url: String
        get() = "$baseUrl/users/$userId/shared-playlists"
    override val httpMethod: ApiSpec.HttpMethod
        get() = ApiSpec.HttpMethod.GET
    override val parameters: Map<String, String>
        get() = super.parameters.toMutableMap().apply {
            this["offset"] = offset.toString()
        }


    private data class RootEntity(
            @SerializedName("data") val data: List<PlaylistInfoEntity>,
            @SerializedName("paging") val paging: PagingEntity
    )

    class ApiResult(
            val playlistInfoList: List<PlaylistInfo>,
            val paging: Paging
    )
}