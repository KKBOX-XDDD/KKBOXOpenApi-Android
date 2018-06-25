package com.kkbox.openapi

import com.kkbox.openapi.infrastructure.implementation.FakeAsyncManager
import com.kkbox.openapi.infrastructure.implementation.Java8Crypto
import com.kkbox.openapi.infrastructure.implementation.OkhttpRequestExecutor
import com.kkbox.openapi.tools.Logger
import okhttp3.OkHttpClient
import org.junit.Before

open class ApiTestBase {

    companion object {
        var isGotToken = false
    }

    protected val log: Logger.LoggerSpec = Logger.UnitTestLogger()

    @Before
    fun setup() {
        if (!isGotToken) {

            KKBOXOpenApi.install(
                    "fc87971f683fd619ba46be6e3aa2cbc2",
                    "5b70cd567551d03d4c43c5cec9e02d1a",
                    OkhttpRequestExecutor(OkHttpClient(), true),
                    FakeAsyncManager()
            )
            KKBOXOpenApi.update(Java8Crypto())

            KKBOXOpenApi.fetchAuthToken({ throw AssertionError("Fetch token fail.") }) {
                isGotToken = true
                System.out.printf("Fetch token success\n")
            }

        }
    }

    protected fun onApiError(e: Error?, message: String) {
        throw AssertionError("$message: $e")
    }
}