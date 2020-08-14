package com.devsan.notimotelibrary

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.devsan.notimote.Notimote

class MainActivity : AppCompatActivity() {
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
                initTextPlaylist { "새로운시작" }
                setLayoutVisible(
                    arrayOf(Notimote.SOUND, Notimote.CHANNEL, Notimote.PLAYSTOP),
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
    }

    override fun onResume() {
        super.onResume()
        val button = findViewById<Button>(R.id.Main_button)
        button.setOnClickListener {
            notimote.setTextPlaylist("change")
        }
    }
}