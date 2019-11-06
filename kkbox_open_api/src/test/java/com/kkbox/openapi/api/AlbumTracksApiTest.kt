package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import kotlinx.coroutines.runBlocking
import me.showang.respect.core.error.RequestError
import me.showang.respect.start
import org.junit.Test

class AlbumTracksApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        println("\n${this.javaClass.simpleName} testRequest_success")
        runBlocking {
            AlbumTracksApi("KmRKnW5qmUrTnGRuxF")
                    .start(this, {
                        if (it is RequestError) {
                            println("code: ${it.responseCode}, ${String(it.bodyBytes
                                    ?: byteArrayOf())}")
                        }
                        log.print(it.toString())
                        throw AssertionError("testRequest_success fail")
                    }) {
                        log.print("id: ${it.tracks.first().id}")
                        log.print("web side: ${it.tracks.first().webUrl}")
                        log.print("limit count: ${it.tracks.size}")
                        log.print("offset: ${it.paging.offset}")
                        assert(it.tracks.isNotEmpty()) {
                            "Tracks may not be empty."
                        }
                    }
        }
    }


}