package com.example.jeky

import android.app.Application
import com.example.jeky.core.data.JekyContainerImpl
import com.example.jeky.core.domain.JekyContainer

class JekyApplication : Application() {
    lateinit var jekyContainer: JekyContainer

    override fun onCreate() {
        super.onCreate()
        jekyContainer = JekyContainerImpl(applicationContext)
    }
}