package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import kotlinx.coroutines.runBlocking
import me.showang.respect.core.error.RequestError
import me.showang.respect.start
import org.junit.Test

class UserSharedPlaylistApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        runBlocking {
            UserSharedPlaylistApi("GlKSG5huISe0wwp73O").start(this, { e ->
                if (e is RequestError) {
                    print("error code: ${e.responseCode}, ${String(e.bodyBytes ?: byteArrayOf())}")
                }
                onApiError(e, "testRequest_success fail.")
            }) {
                assert(it.paging.hasNextPage)
                assert(it.paging.offset == 50)
                assert(it.playlistInfoList.size == 50)
            }
        }
    }

}