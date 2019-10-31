package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import kotlinx.coroutines.runBlocking
import me.showang.respect.start
import org.junit.Test

class PlaylistApiTest : ApiTestBase() {

  @Test
  fun testRequest_success() {
    println("\n${this.javaClass.simpleName} testRequest_success")
    runBlocking {
      PlaylistApi("4nUZM-TY2aVxZ2xaA-")
              .start(this, {
                throw AssertionError("testRequest_success fail")
              }) {
                log.print("Playlist: ${it.playlist.info.title}")
                log.print("track size: ${it.playlist.tracks.size}")
                log.print("Has next page? ${it.paging.hasNextPage}")
                assert(it.playlist.tracks.isNotEmpty()) {
                  "Tracks may not be empty."
                }
              }
    }
  }

}