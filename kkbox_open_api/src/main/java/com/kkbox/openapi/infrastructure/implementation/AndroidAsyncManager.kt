package com.kkbox.openapi.infrastructure.implementation

import com.kkbox.openapi.infrastructure.AsyncManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AndroidAsyncManager: AsyncManager {

    override fun <Result> start(background: () -> Result, uiThread: (Result) -> Unit) {
        doAsync {
            val result = background()
            uiThread {
                uiThread(result)
            }
        }
    }

}