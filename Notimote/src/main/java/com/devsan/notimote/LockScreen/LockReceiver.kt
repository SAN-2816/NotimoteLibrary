package com.devsan.notimote.LockScreen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LockReceiver : BroadcastReceiver() {
    var first: Long = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_SCREEN_OFF) {//화면이 꺼지면
            val newIntent = Intent(context, LockScreenActivity::class.java)// 액티비티 생성
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(newIntent) // 액티비티 시작
        }
    }
}