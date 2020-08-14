package com.devsan.notimotelibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class LockReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_SCREEN_ON -> {
                Log.e("lock", "on")
            }
            Intent.ACTION_SCREEN_OFF -> {
                Log.e("lock", "off")
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.e("lock", "complete")
                val intent = Intent(context, LockService::class.java)
                context?.startService(intent)
            }
        }
    }
}