package com.kkbox.openapi.api

import com.google.gson.Gson
import com.kkbox.openapi.api.entities.AlbumEntity
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import me.showang.respect.core.HttpMethod

/**
 * Fetches metadata of an album.
 *
 * See https://docs-zhtw.kkbox.codes/v1.1/reference#albums_album_id
 *
 * @property albumId the ID of the desired album.
 */
class AlbumApi(private val albumId: String) :
        OpenApiBase<AlbumEntity>() {

    override val url: String
        get() = "$baseUrl/albums/$albumId"
    override val httpMethod: HttpMethod
        get() = HttpMethod.GET

    override fun parse(bytes: ByteArray): AlbumEntity {
        val gson = Gson()
        val json = String(bytes)
        return gson.fromJson(json, AlbumEntity::class.java)
    }
}