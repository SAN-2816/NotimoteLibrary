package com.devsan.notimote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

class Notimote(context: Context, channelId: String, javaClass: Class<*>) {
    //노티 커스텀 뷰 생성 1. 큰 레이아웃 2. 작은 레이아웃
    private val smallNotificationLayout =
        RemoteViews(context.packageName, R.layout.notimote_layout_small)
    private val bigNotificationLayout =
        RemoteViews(context.packageName, R.layout.notimote_layout_big)
    private val mContext = context
    private val mChannelId = channelId
    private val mJavaClass = javaClass
    private fun makePendingIntent(name: String?): PendingIntent? {
        val intent = Intent(mContext, mJavaClass)
        intent.action = name
        return PendingIntent.getBroadcast(mContext, mChannelId.toInt(), intent, 0)
    }

    fun setPowerLayout(powerAction: String, playListText: String) {
        smallNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_power,
            makePendingIntent(powerAction)
        )
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_power,
            makePendingIntent(powerAction)
        )
        smallNotificationLayout.setTextViewText(R.id.notimote_TextView_playlist, playListText)
        bigNotificationLayout.setTextViewText(R.id.notimote_TextView_playlist, playListText)
    }

    fun setSoundLayout(visible: Int, downAction: String, upAction: String) {
        bigNotificationLayout.setViewVisibility(R.id.notimote_layout_sound, visible)
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_soundDown,
            makePendingIntent(downAction)
        )
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_soundUp,
            makePendingIntent(upAction)
        )
    }

    fun setChannelLayout(visible: Int, downAction: String, upAction: String) {
        bigNotificationLayout.setViewVisibility(R.id.notimote_layout_channel, visible)
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_channel_channelDown,
            makePendingIntent(downAction)
        )
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_channel_channelUp,
            makePendingIntent(upAction)
        )
    }

    fun setPlayStopLayout(
        visible: Int,
        rewindAction: String,
        stopAction: String,
        playAction: String,
        forwardAction: String
    ) {
        bigNotificationLayout.setViewVisibility(R.id.notimote_layout_playstop, visible)
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_rewind,
            makePendingIntent(rewindAction)
        )
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_stop,
            makePendingIntent(stopAction)
        )
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_play,
            makePendingIntent(playAction)
        )
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_forward,
            makePendingIntent(forwardAction)
        )
    }

    fun setHomeLayout(visible: Int, exitAction: String, homeAction: String, beforeAction: String) {
        bigNotificationLayout.setViewVisibility(R.id.notimote_layout_home, visible)
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_exit,
            makePendingIntent(exitAction)
        )
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_home,
            makePendingIntent(homeAction)
        )
        bigNotificationLayout.setOnClickPendingIntent(
            R.id.notimote_ImageButton_before,
            makePendingIntent(beforeAction)
        )
    }

    fun createNotify(notificationManager: NotificationManager, iconID: Int) {
        val customNotification: NotificationCompat.Builder =
            NotificationCompat.Builder(mContext, mChannelId)
                .setSmallIcon(iconID)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(smallNotificationLayout)
                .setCustomBigContentView(bigNotificationLayout)
                .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notimote"
            val descriptionText = "Notify RemoteController"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(mChannelId, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(mChannelId.toInt(), customNotification.build())
        }
    }
}