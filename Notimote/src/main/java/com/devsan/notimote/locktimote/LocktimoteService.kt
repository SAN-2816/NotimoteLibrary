package com.devsan.notimote.locktimote

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class LocktimoteService : Service() {
    private val lockReceiver: LocktimoteReceiver = LocktimoteReceiver()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(Intent.ACTION_SCREEN_OFF) // 스크린 OFF 시 동작.
        registerReceiver(lockReceiver, filter) //브로드캐스트리시버를 등록.
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        //서비스를 강제 종료했을때, 서비스를 어떤 방법으로 다시 시작시킬지 결정.
        super.onStartCommand(intent, flags, startId)

        return Service.START_REDELIVER_INTENT //이후 서비스 재생성 가능, 강제로 종료되기 전에 전달된 마지막 Intent를 다시 전달해주는 기능 포함.
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(lockReceiver)
    }
}