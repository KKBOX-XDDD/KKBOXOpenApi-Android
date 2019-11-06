package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import kotlinx.coroutines.runBlocking
import me.showang.respect.core.error.RequestError
import me.showang.respect.start
import org.junit.Test

class PlaylistTracksApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        println("\n${this.javaClass.simpleName} testRequest_success")
        runBlocking {
            PlaylistTracksApi("Ot9b9neLPHGat4LYK-")
                    .start(this, {
                        if (it is RequestError) {
                            println("code: ${it.responseCode}, ${String(it.bodyBytes
                                    ?: byteArrayOf())}")
                        }
                        throw AssertionError("testRequest_success fail")
                    }) {
                        log.print("id: ${it.tracks.first().id}")
                        log.print("web side: ${it.tracks.first().webUrl}")
                        log.print("limit count: ${it.tracks.size}")
                        log.print("offset: ${it.paging.offset}")
                        assert(it.tracks.isNotEmpty()) {
                            "Tracks may not be empty."
                        }
                        assert(it.paging.hasNextPage) {
                            "This playlist might have next offset."
                        }
                        assert(100 == it.paging.offset) {
                            "Offset may be 100"
                        }
                    }
        }
    }

    @Test
    fun testRequest_pagingSuccess() {
        println("\n${this.javaClass.simpleName} testRequest_pagingSuccess")
        runBlocking {
            PlaylistTracksApi("Ot9b9neLPHGat4LYK-").offset(100)
                    .start(this, {
                        throw AssertionError("testRequest_success fail")
                    }) {
                        log.print("limit count: ${it.tracks.size}")
                        log.print("has next offset: ${it.paging.hasNextPage}")
                        log.print("offset: ${it.paging.offset}")
                        assert(!it.paging.hasNextPage)
                    }
        }
    }
}
