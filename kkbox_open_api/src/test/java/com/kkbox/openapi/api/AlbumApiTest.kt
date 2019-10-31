package com.kkbox.openapi.api

import AlbumApi
import com.kkbox.openapi.ApiTestBase
import kotlinx.coroutines.runBlocking
import me.showang.respect.start
import org.junit.Assert.*

class AlbumApiTest : ApiTestBase() {
  fun test_fetchAlbum() {
    runBlocking {
      AlbumApi("KmRKnW5qmUrTnGRuxF").start(this, { throw AssertionError("testRequest_success fail") }) {
        assert(it.name.isNotEmpty()) {
          "album name not be empty."
        }
      }
    }
  }
}