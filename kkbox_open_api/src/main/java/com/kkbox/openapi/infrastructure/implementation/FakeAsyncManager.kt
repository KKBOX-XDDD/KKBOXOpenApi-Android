package com.kkbox.openapi.infrastructure.implementation

import com.kkbox.openapi.infrastructure.AsyncManager

class FakeAsyncManager: AsyncManager {

    override fun <Result> start(background: () -> Result, uiThread: (Result) -> Unit) {
        uiThread(background())
    }

}