import com.google.gson.Gson
import com.kkbox.openapi.api.AlbumEntity
import com.kkbox.openapi.api.territoryToString
import com.kkbox.openapi.infrastructure.implementation.OpenApiBase
import com.kkbox.openapi.model.Territory
import me.showang.respect.core.HttpMethod

/**
 * Fetches metadata of an album.
 *
 * See https://docs-zhtw.kkbox.codes/v1.1/reference#albums_album_id
 */
class AlbumApi(private val albumId: String, private val territory: Territory = Territory.TW) :
        OpenApiBase<AlbumEntity>() {

  override val url: String
    get() = "$baseUrl/albums/$albumId"
  override val httpMethod: HttpMethod
    get() = HttpMethod.GET
  override val urlQueries: Map<String, String>
    get() = super.urlQueries.toMutableMap().apply {
      this["territory"] = territoryToString(territory)
    }

  override fun parse(bytes: ByteArray): AlbumEntity {
    val gson = Gson()
    val json = String(bytes)
    val album = gson.fromJson(json, AlbumEntity::class.java)
    return album
  }
}