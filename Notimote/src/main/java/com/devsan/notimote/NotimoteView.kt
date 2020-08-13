package com.devsan.notimote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

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

    // SDK 26 이상
    fun createView(
        notificationManager: NotificationManager,
        notificationChannel: NotificationChannel,
        iconID: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel.vibrationPattern = longArrayOf(0) // 알림창 생성 진동 끄기
            val customNotification: NotificationCompat.Builder =
                buildNotification(notificationChannel.id, iconID)
            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(
                notificationChannel.id.toInt(),
                customNotification.build()
            )
        }
    }

    // SDK 26 미만
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

    fun setTextPowerPlaylist(text: String) {
        mSmallNotificationLayout.setTextViewText(R.id.notimote_TextView_playlist, text)
        mBigNotificationLayout.setTextViewText(R.id.notimote_TextView_playlist, text)
    }

    fun setLayoutVisible(receiver: Class<*>, channelID: String, layout: String, visible: Int) {
        when (layout) {
            Notimote.POWER -> {
                mSmallNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_power,
                    makePendingIntent(receiver, channelID, Notimote.POWER_BUTTON)
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_power,
                    makePendingIntent(receiver, channelID, Notimote.POWER_BUTTON)
                )
            }
            Notimote.SOUND -> {
                mBigNotificationLayout.setViewVisibility(R.id.notimote_layout_sound, visible)
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_soundUp,
                    makePendingIntent(receiver, channelID, Notimote.SOUND_BUTTON_UP)
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_soundDown,
                    makePendingIntent(receiver, channelID, Notimote.SOUND_BUTTON_DOWN)
                )
            }
            Notimote.CHANNEL -> {
                mBigNotificationLayout.setViewVisibility(
                    R.id.notimote_layout_channel,
                    visible
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_channel_channelUp,
                    makePendingIntent(receiver, channelID, Notimote.CHANNEL_BUTTON_UP)
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_channel_channelDown,
                    makePendingIntent(receiver, channelID, Notimote.CHANNEL_BUTTON_DOWN)
                )
            }
            Notimote.PLAYSTOP -> {
                mBigNotificationLayout.setViewVisibility(
                    R.id.notimote_layout_playstop,
                    visible
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_rewind,
                    makePendingIntent(receiver, channelID, Notimote.PLAYSTOP_BUTTON_REWIND)
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_play,
                    makePendingIntent(receiver, channelID, Notimote.PLAYSTOP_BUTTON_PLAY)
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_stop,
                    makePendingIntent(receiver, channelID, Notimote.PLAYSTOP_BUTTON_STOP)
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_forward,
                    makePendingIntent(receiver, channelID, Notimote.PLAYSTOP_BUTTON_FORWARD)
                )
            }
            Notimote.HOME -> {
                mBigNotificationLayout.setViewVisibility(
                    R.id.notimote_layout_home,
                    visible
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_exit,
                    makePendingIntent(receiver, channelID, Notimote.HOME_BUTTON_EXIT)
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_home,
                    makePendingIntent(receiver, channelID, Notimote.HOME_BUTTON_HOME)
                )
                mBigNotificationLayout.setOnClickPendingIntent(
                    R.id.notimote_ImageButton_before,
                    makePendingIntent(receiver, channelID, Notimote.HOME_BUTTON_BEFORE)
                )
            }
        }
    }
}