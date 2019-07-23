package com.kkbox.openapi.tools

import android.util.Log

class Logger {

    companion object {

        private var loggerImpl = EmptyLogger() as LoggerSpec

        fun initImpl(loggerImpl: LoggerSpec) {
            this.loggerImpl = loggerImpl
        }

        fun e(tag: String, message: String) {
            loggerImpl.e(tag, message)
        }

        fun w(tag: String, message: String) {
            loggerImpl.w(tag, message)
        }

        fun d(tag: String, message: String) {
            loggerImpl.d(tag, message)
        }

        fun i(tag: String, message: String) {
            loggerImpl.i(tag, message)
        }

        fun print(message: String) {
            loggerImpl.print(message)
        }

    }

    interface LoggerSpec {
        fun e(tag: String, message: String)
        fun w(tag: String, message: String)
        fun d(tag: String, message: String)
        fun i(tag: String, message: String)
        fun print(message: String)
    }

    class EmptyLogger : LoggerSpec {
        override fun e(tag: String, message: String) {}

        override fun w(tag: String, message: String) {}

        override fun d(tag: String, message: String) {}

        override fun i(tag: String, message: String) {}

        override fun print(message: String) {}
    }

    class AndroidLogger : LoggerSpec {
        override fun e(tag: String, message: String) {
            Log.e(tag, message)
        }

        override fun w(tag: String, message: String) {
            Log.w(tag, message)
        }

        override fun d(tag: String, message: String) {
            Log.d(tag, message)
        }

        override fun i(tag: String, message: String) {
            Log.i(tag, message)
        }

        override fun print(message: String) {
            i("System", message)
        }
    }

    class UnitTestLogger : LoggerSpec {
        override fun e(tag: String, message: String) {
            println("[ERROR] $tag: $message")
        }

        override fun w(tag: String, message: String) {
            println("[WARNING] $tag: $message")
        }

        override fun d(tag: String, message: String) {
            println("[DEBUG] $tag: $message")
        }

        override fun i(tag: String, message: String) {
            println("[INFO] $tag: $message")
        }

        override fun print(message: String) {
            println(message)
        }

    }

}