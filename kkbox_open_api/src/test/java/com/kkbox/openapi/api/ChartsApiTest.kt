package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import kotlinx.coroutines.runBlocking
import me.showang.respect.start
import org.junit.Test

class ChartsApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        println("\n${this.javaClass.simpleName} testRequest_success")
        runBlocking {
            ChartsApi().start(this, {
                throw AssertionError("testRequest_success fail")
            }) {
                log.print("Featured Playlist Size: ${it.playlistList.size}")
                log.print("Has next offset? ${it.paging.hasNextPage}")
                assert(it.playlistList.isNotEmpty()) {
                    "Playlist may not be empty."
                }
            }
        }
    }

}