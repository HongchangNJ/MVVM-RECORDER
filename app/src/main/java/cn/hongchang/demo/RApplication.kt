package cn.hongchang.demo

import android.app.Application

class RApplication: Application() {
    companion object {
        var INSTANCE:Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}