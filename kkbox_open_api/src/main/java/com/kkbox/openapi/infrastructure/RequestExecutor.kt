package com.kkbox.openapi.infrastructure

interface RequestExecutor {
    fun request(api:ApiSpec, failCallback:(error:Error)->Unit, completeCallback:(response:ByteArray)->Unit, tag:Any = api)
}