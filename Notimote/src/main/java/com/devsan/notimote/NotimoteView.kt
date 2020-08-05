package com.devsan.notimote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

// 레아이웃 설정 및 버튼 설정 추가 해야함.
class NotimoteView(context: Context) {
    private val mContext = context

    private val mSmallNotificationLayout: RemoteViews =
        RemoteViews(context.packageName, R.layout.notimote_layout_small)
    private val mBigNotificationLayout: RemoteViews =
        RemoteViews(context.packageName, R.layout.notimote_layout_big)

    private fun makePendingIntent(
        receiver: Class<*>,
        channelID: String,
        name: String?
    ): PendingIntent? {
        val intent = Intent(mContext, receiver)
        intent.action = name
        return PendingIntent.getBroadcast(mContext, channelID.toInt(), intent, 0)
    }

    private fun buildNotification(channelID: String, iconID: Int): NotificationCompat.Builder {
        val icon: Int = if (iconID == 0) {
            R.drawable.ic_baseline_settings_remote_24
        } else {
            iconID
        }
        return NotificationCompat.Builder(mContext, channelID)
            .setSmallIcon(icon)// 아이콘 받아야함...
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(mSmallNotificationLayout)
            .setCustomBigContentView(mBigNotificationLayout)
            .setOngoing(true)
    }

    fun setLayoutVisible(receiver: Class<*>, channelID: String, layout: String, visible: Int) {
        when (layout) {
            Notimote.SOUND -> {
                mBigNotificationLayout.setViewVisibility(R.id.notimote_layout_sound, visible)
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_soundUp,
                    makePendingIntent(receiver, channelID, Notimote.CHANNEL_UP)
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_soundDown,
                    makePendingIntent(receiver, channelID, Notimote.SOUND_DOWN)
                )
            }
            Notimote.CHANNEL -> mBigNotificationLayout.setViewVisibility(
                R.id.notimote_layout_channel,
                visible
            )
            Notimote.PLAYSTOP -> mBigNotificationLayout.setViewVisibility(
                R.id.notimote_layout_playstop,
                visible
            )
            Notimote.HOME -> mBigNotificationLayout.setViewVisibility(
                R.id.notimote_layout_home,
                visible
            )
        }
    }

    fun createView(
        notificationManager: NotificationManager,
        notificationChannel: NotificationChannel,
        iconID: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val customNotification: NotificationCompat.Builder =
                buildNotification(notificationChannel.id, iconID)
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(
                notificationChannel.id.toInt(),
                customNotification.build()
            )
        }
    }

    fun createView(
        notificationManager: NotificationManager,
        channelID: String,
        iconID: Int
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            val customNotification: NotificationCompat.Builder =
                buildNotification(channelID, iconID)
            notificationManager.notify(
                channelID.toInt(),
                customNotification.build()
            )
        }
    }
}