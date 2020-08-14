package com.devsan.notimotelibrary

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class LockService : Service() {
    private val mReceiver = LockReceiver()

    override fun onCreate() {
        super.onCreate()
        val filter =  IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(mReceiver, filter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null){
            val filter =  IntentFilter(Intent.ACTION_SCREEN_ON)
            filter.addAction(Intent.ACTION_SCREEN_OFF)
            registerReceiver(mReceiver, filter)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}