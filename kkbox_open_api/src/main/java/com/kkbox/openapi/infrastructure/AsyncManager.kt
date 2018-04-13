package com.kkbox.openapi.infrastructure

interface AsyncManager {

    fun<Result> start(background:()->Result, uiThread:(Result)->Unit)

}