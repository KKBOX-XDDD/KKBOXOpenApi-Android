package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import org.junit.Test

class ChartsApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        System.out.println("\n${this.javaClass.simpleName} testRequest_success")
        ChartsApi()
                .startRequest({
                    throw AssertionError("testRequest_success fail")
                }) {
                    System.out.println("Featured Playlist Size: ${it.playlistList.size}")
                    System.out.println("Has next page? ${it.paging.hasNextPage}")
                }
    }

}