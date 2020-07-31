package com.devsan.notimotelibrary

import android.app.NotificationManager
import android.content.Context
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
        notimoteView {
            with { this@MainActivity }
            channel { "1000" }
            javaClass { NotimoteReceiver::class.java }
            notificationManager{notificationManager}
        }
//        val notimote = Notimote(this,"1010", NotimoteReceiver::class.java)
//        notimote.setPlayStopLayout(View.VISIBLE, "rewind", "stop", "play", "forward")
//        notimote.setHomeLayout(View.VISIBLE, "exit", "home", "before")
//        notimote.createNotify(notificationManager)
    }

    fun notimoteView(lambda: Notimote.() -> Unit) = Notimote().apply(lambda).build()
}