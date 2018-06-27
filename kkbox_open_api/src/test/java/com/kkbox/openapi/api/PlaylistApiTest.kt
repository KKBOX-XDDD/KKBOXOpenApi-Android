package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import org.junit.Test

class PlaylistApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        System.out.println("\n${this.javaClass.simpleName} testRequest_success")
        PlaylistApi("4nUZM-TY2aVxZ2xaA-")
                .startRequest({
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