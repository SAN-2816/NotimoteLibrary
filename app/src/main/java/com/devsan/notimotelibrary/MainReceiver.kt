package com.devsan.notimotelibrary

import android.content.Context
import android.content.Intent
import android.util.Log
import com.devsan.notimote.NotimoteReceiver

class MainReceiver :NotimoteReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("receiver", intent?.action)
    }
}