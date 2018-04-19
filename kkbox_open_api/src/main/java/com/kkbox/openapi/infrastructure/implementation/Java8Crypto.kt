package com.kkbox.openapi.infrastructure.implementation

import android.annotation.SuppressLint
import com.kkbox.openapi.infrastructure.Crypto
import java.util.*

class Java8Crypto: Crypto {

    @SuppressLint("NewApi")
    override fun base64(input: String): String {
        return Base64.getEncoder().encodeToString(input.toByteArray())
    }

}