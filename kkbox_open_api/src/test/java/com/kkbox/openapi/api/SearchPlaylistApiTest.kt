package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import org.junit.Test

class SearchPlaylistApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        SearchPlaylistApi("KKTIX")
                .startRequest({
                    throw AssertionError("testRequest_success fail")
                }, {
                    System.out.println(it.playlistInfoList)
                    System.out.println(it.paging)
                    assert(it.playlistInfoList.isNotEmpty()) {
                        "Where is 『KKTIX倒台大全集』??"
                    }
                })
    }

}