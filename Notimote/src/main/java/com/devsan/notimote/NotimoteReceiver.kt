package com.devsan.notimote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.devsan.notimote.NotimoteView

abstract class NotimoteReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {}
}