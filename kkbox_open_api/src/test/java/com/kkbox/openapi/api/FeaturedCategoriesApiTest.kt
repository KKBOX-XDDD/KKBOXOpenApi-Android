package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import org.junit.Test

class FeaturedCategoriesApiTest:ApiTestBase() {

    @Test
    fun testRequest_success() {
        System.out.println("\n${this.javaClass.simpleName} testRequest_success")
        FeaturedCategoriesApi()
                .startRequest({
                    throw AssertionError("testRequest_success fail")
                }) {
                    System.out.println("Featured Playlist Size: ${it.categoryList.size}")
                    System.out.println("Has next page? ${it.paging.hasNextPage}")
                }
    }

}