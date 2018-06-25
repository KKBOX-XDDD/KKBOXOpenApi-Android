package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import org.junit.Test

class TracksApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        System.out.println("\n${this.javaClass.simpleName} testRequest_success")
        TracksApi("Ot9b9neLPHGat4LYK-")
                .startRequest({
                    throw AssertionError("testRequest_success fail")
                }) {
                    System.out.println("item count: ${it.tracks.size}")
                    System.out.println("offset: ${it.paging.offset}")
                    assert(it.tracks.isNotEmpty()) {
                        "Tracks may not be empty."
                    }
                    assert(it.paging.hasNextPage) {
                        "This playlist might have next page."
                    }
                    assert(100 == it.paging.offset) {
                        "Offset may be 100"
                    }
                }
    }

    @Test
    fun testRequest_pagingSuccess() {
        System.out.println("\n${this.javaClass.simpleName} testRequest_pagingSuccess")
        TracksApi("Ot9b9neLPHGat4LYK-").offset(100)
                .startRequest({
                    throw AssertionError("testRequest_success fail")
                }) {
                    System.out.println("item count: ${it.tracks.size}")
                    System.out.println("has next page: ${it.paging.hasNextPage}")
                    System.out.println("offset: ${it.paging.offset}")
                    assert(!it.paging.hasNextPage)
                }
    }
}