package com.devsan.notimotelibrary

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.devsan.notimote.Notimote

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notimote"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("1010", name, importance)

            Notimote().init {
                with { this@MainActivity }
                receiverClass { MainReceiver::class.java }
                notificationManager { notificationManager }
                notificationChannel { channel }
                iconID { R.drawable.ic_launcher_background }
                setLayoutVisible(arrayOf(Notimote.SOUND, Notimote.CHANNEL, Notimote.HOME, Notimote.PLAYSTOP), View.VISIBLE)
            }
        }else{
            Notimote().init {
                with { this@MainActivity }
                receiverClass { MainReceiver::class.java }
                notificationManager { notificationManager }
                channel { "1010" }
                setLayoutVisible(arrayOf(Notimote.SOUND, Notimote.CHANNEL, Notimote.PLAYSTOP), View.VISIBLE)
            }
        }
    }
}