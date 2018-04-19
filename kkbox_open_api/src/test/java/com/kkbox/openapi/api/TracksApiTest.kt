package com.kkbox.openapi.api

import com.kkbox.openapi.ApiTestBase
import junit.framework.Assert
import org.junit.Test

class TracksApiTest: ApiTestBase() {

    @Test
    fun testRequest_success() {
        System.out.println("\n${this.javaClass.simpleName} testRequest_success")
        TracksApi("Ot9b9neLPHGat4LYK-")
                .startRequest({
                    throw AssertionError("testRequest_success fail")
                }) {
                    System.out.println("item count: ${it.tracks.size}")
                    System.out.println("has next page: ${it.paging.hasNextPage}")
                    System.out.println("offset: ${it.paging.offset}")
                    Assert.assertTrue(it.paging.hasNextPage)
                    Assert.assertEquals(100, it.paging.offset)
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
                    Assert.assertFalse(it.paging.hasNextPage)
                }
    }
}