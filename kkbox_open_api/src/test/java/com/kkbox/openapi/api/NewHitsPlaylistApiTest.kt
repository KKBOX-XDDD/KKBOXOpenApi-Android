package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import org.junit.Test

class NewHitsPlaylistApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        System.out.println("\n${this.javaClass.simpleName} testRequest_success")
        NewHitsPlaylistApi()
                .startRequest({
                    throw AssertionError("testRequest_success fail")
                }) {
                    log.print("Featured Playlist Size: ${it.playlistList.size}")
                    log.print("Has next page? ${it.paging.hasNextPage}")
                    assert(it.playlistList.isNotEmpty()) {
                        "New Hits Playlist may not be empty."
                    }
                }
    }

}