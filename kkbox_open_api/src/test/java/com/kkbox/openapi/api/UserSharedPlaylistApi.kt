package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import org.junit.Test

class UserSharedPlaylistApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        UserSharedPlaylistApi("GlKSG5huISe0wwp73O")
                .startRequest({
                    onApiError(it, "testRequest_success fail.")
                }, {
                    log.print("Item count: ${it.playlistInfoList.size}")
                    log.print("Has next page: ${it.paging.hasNextPage}")
                    log.print("Offset: ${it.paging.offset}")
                })
    }

}