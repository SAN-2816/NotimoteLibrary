package com.devsan.notimote.locktimote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LocktimoteReceiver:BroadcastReceiver() {
    var first: Long = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        //스크린 OFF
        if (intent?.action == Intent.ACTION_SCREEN_OFF) {
            val newIntent = Intent(context, LocktimoteActivity::class.java)// 잠금 액티비티 생성
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(newIntent) // 액티비티 시작
        }
    }
}