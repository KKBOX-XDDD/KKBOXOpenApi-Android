package com.kkbox.openapi.infrastructure.implementation

import android.util.Base64
import android.util.Base64.NO_WRAP
import com.kkbox.openapi.infrastructure.Crypto

class AndroidCrypto : Crypto {

    override fun base64(input: String): String {
        val inputByteArray: ByteArray = input.toByteArray()
        return Base64.encodeToString(inputByteArray, NO_WRAP)
    }

}