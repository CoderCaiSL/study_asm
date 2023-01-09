package com.caisl.study_asm

import android.app.Application

/**
 * @author: CaiSongL
 * @date: 2023/1/8 1:15
 */
class MyApplication : Application() {


    companion object{
        lateinit var instance : Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

}