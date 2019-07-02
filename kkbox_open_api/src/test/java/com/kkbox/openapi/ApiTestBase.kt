package com.kkbox.openapi

import com.kkbox.openapi.infrastructure.implementation.Java8Crypto
import com.kkbox.openapi.tools.Logger
import kotlinx.coroutines.runBlocking
import me.showang.respect.okhttp.OkhttpRequestExecutor
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
            runBlocking {
                KKBOXOpenApi.install(
                        "fc87971f683fd619ba46be6e3aa2cbc2",
                        "5b70cd567551d03d4c43c5cec9e02d1a",
                        OkhttpRequestExecutor(OkHttpClient())
                )
                KKBOXOpenApi.update(Java8Crypto())
                KKBOXOpenApi.fetchAuthToken({ throw AssertionError("Fetch token fail.") }) {
                    isGotToken = true
                    System.out.printf("Fetch token success\n")
                }
            }
        }
    }

    protected fun onApiError(e: Throwable?, message: String) {
        throw AssertionError("$message: $e")
    }
}