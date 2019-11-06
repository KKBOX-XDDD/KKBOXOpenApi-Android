package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import kotlinx.coroutines.runBlocking
import me.showang.respect.start
import org.junit.Test

class SearchPlaylistApiTest : ApiTestBase() {

    @Test
    fun testRequest_success() {
        runBlocking {
            SearchPlaylistApi("KKTIX")
                    .start(this, {
                        throw AssertionError("testRequest_success fail")
                    }) {
                        log.print("Item count: ${it.playlistInfoList.size}")
                        log.print("offset: ${it.paging.offset}")
                        assert(it.playlistInfoList.isNotEmpty()) {
                            "Where is 『KKTIX倒台大全集』??"
                        }
                    }
        }
    }

}
