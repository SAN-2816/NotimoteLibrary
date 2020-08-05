package com.devsan.notimotelibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.devsan.notimote.NotimoteView

class NotimoteReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("receive", intent?.action)

    }
}