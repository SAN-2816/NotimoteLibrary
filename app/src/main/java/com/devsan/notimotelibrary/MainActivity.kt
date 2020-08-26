package com.devsan.notimotelibrary

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.devsan.notimote.Notimote

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val NOTIMOTE_CHANNEL = 1010
    }

    private val notimote: Notimote = Notimote()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notimote"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIMOTE_CHANNEL.toString(), name, importance)

            notimote.init {
                with { this@MainActivity }
                receiverClass { MainReceiver::class.java }
                notificationManager { notificationManager }
                notificationChannel { channel }
                initTextPlaylist { "재생목록" }
                setLayoutVisible(
                    arrayOf(Notimote.SOUND, Notimote.CHANNEL, Notimote.PLAYSTOP, Notimote.HOME),
                    View.VISIBLE
                )
            }
        } else {
            notimote.init {
                with { this@MainActivity }
                receiverClass { MainReceiver::class.java }
                notificationManager { notificationManager }
                channel { NOTIMOTE_CHANNEL.toString() }
                setLayoutVisible(
                    arrayOf(Notimote.SOUND, Notimote.CHANNEL, Notimote.PLAYSTOP),
                    View.VISIBLE
                )
            }
        }
        val button = findViewById<Button>(R.id.Main_button)
        button.setOnClickListener {
            notimote.setTextPlaylist("change")
        }
    }

    override fun onClick(v: View?) {
        Log.e("Main", v?.id.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIMOTE_CHANNEL)
    }
}