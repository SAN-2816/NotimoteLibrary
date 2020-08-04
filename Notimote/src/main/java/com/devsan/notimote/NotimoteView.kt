package com.devsan.notimote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

class NotimoteView {
    private lateinit var smallNotificationLayout: RemoteViews
    private lateinit var bigNotificationLayout: RemoteViews

    fun createView(
        mContext: Context,
        mChannel: String,
        notificationManager: NotificationManager
    ) {
        smallNotificationLayout = RemoteViews(mContext.packageName, R.layout.notimote_layout_small)
        bigNotificationLayout = RemoteViews(mContext.packageName, R.layout.notimote_layout_big)

        val customNotification: NotificationCompat.Builder =
            NotificationCompat.Builder(mContext, mChannel)
                .setSmallIcon(R.drawable.ic_baseline_settings_remote_24)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(smallNotificationLayout)
                .setCustomBigContentView(bigNotificationLayout)
                .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notimote"
            val descriptionText = "Notify RemoteController"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(mChannel, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(mChannel.toInt(), customNotification.build())
        } else {//SDK 26 미만... 처리해줘야함.
            //customNotification.notification
        }
    }
}

//            val name = "Notimote"
//            val descriptionText = "Notify RemoteController"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(mChannel, name, importance).apply {
//                description = descriptionText
//            }