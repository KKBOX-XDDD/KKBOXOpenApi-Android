package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import kotlinx.coroutines.runBlocking
import me.showang.respect.start
import org.junit.Test

class NewHitsPlaylistApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        println("\n${this.javaClass.simpleName} testRequest_success")
        runBlocking {
            NewHitsPlaylistApi()
                    .start(this, {
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

}