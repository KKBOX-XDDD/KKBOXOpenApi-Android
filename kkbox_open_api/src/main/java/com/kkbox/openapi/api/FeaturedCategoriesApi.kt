package com.kkbox.openapi.api

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.kkbox.openapi.api.entities.ImageEntity
import com.kkbox.openapi.api.entities.PagingEntity
import com.kkbox.openapi.api.entities.SummaryEntity
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.CategoryInfo
import com.kkbox.openapi.model.ImageInfo
import com.kkbox.openapi.model.Paging
import me.showang.respect.core.HttpMethod

/**
 * Fetches featured playlist categories.
 *
 * See https://docs-zhtw.kkbox.codes/reference#featured-playlist-categories
 */
class FeaturedCategoriesApi : OpenApiBase<FeaturedCategoriesApi.ApiResult>() {

    override fun parse(bytes: ByteArray): ApiResult {
        val json = Gson().fromJson(String(bytes), RootEntity::class.java)
        return ApiResult(
                json.data.map {
                    CategoryInfo(
                            it.id,
                            it.title,
                            it.images.mapIndexed { index, imageEntity ->
                                when (index) {
                                    0 -> ImageInfo(ImageInfo.Type.SMALL, imageEntity.url)
                                    else -> ImageInfo(ImageInfo.Type.MEDIUM, imageEntity.url)
                                }
                            }
                    )
                },
                PagingEntity.parse(json.data.size, json.paging, json.summary)
        )
    }

    override val url: String by lazy { "$baseUrl/featured-playlist-categories" }
    override val httpMethod: HttpMethod = HttpMethod.GET

    class ApiResult(val categoryList: List<CategoryInfo>, val paging: Paging)

    private data class RootEntity(
            @SerializedName("data") val data: List<CategoryEntity>,
            @SerializedName("paging") val paging: PagingEntity,
            @SerializedName("summary") val summary: SummaryEntity
    )

    private data class CategoryEntity(
            @SerializedName("id") val id: String,
            @SerializedName("title") val title: String,
            @SerializedName("images") val images: List<ImageEntity>
    )

}