package com.devsan.notimote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

// 베이스 아이콘 필수!!
open class Notimote {
    companion object {
        const val POWER = "notimote.power"
        const val SOUND = "notimote.sound"
        const val SOUND_UP = "notimote.sound_up"
        const val SOUND_DOWN = "notimote.sound_down"
        const val CHANNEL = "notimote.channel"
        const val CHANNEL_UP = "notimote.channel_up"
        const val CHANNEL_DOWN = "notimote.channel_down"
        const val PLAYSTOP = "notimote.playstop"
        const val PLAYSTOP_REWIND = "notimote.playstop_rewind"
        const val PLAYSTOP_STOP = "notimote.playstop_stop"
        const val PLAYSTOP_PLAY = "notimote.playstop_play"
        const val PLAYSTOP_FORWARD = "notimote.playstop_forward"
        const val HOME = "notimote.home"
        const val HOME_EXIT = "notimote.home_exit"
        const val HOME_HOME = "notimote.home_home"
        const val HOME_BEFORE = "notimote.home_befor"
    }

    private lateinit var notimoteView: NotimoteView
    private lateinit var mContext: Context
    private lateinit var mReceiverClass: Class<*>
    private lateinit var mChannel: String // SDK 26 미만을 위해 채널을 String으로 받음.
    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mNotificationChannel: NotificationChannel
    private var mIconID = 0

    //View 생성.
    private fun build() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notimoteView.createView(mNotificationManager, mNotificationChannel, mIconID)
        } else {
            notimoteView.createView(mNotificationManager, mChannel, mIconID)
        }
    }

    //초기화 함수. 외부에서 초기화.
    fun init(lambda: Notimote.() -> Unit) = Notimote().apply(lambda).build()

    fun with(context: () -> Context) {
        this.mContext = context()
        notimoteView = NotimoteView(mContext)
    }

    fun receiverClass(receiverClass: () -> Class<*>) {
        this.mReceiverClass = receiverClass()
    }

    fun channel(channel: () -> String) {
        this.mChannel = channel()
    }

    fun notificationManager(notificationManager: () -> NotificationManager) {
        this.mNotificationManager = notificationManager()
    }

    // 채널 직접 생성.
    fun notificationChannel(id: String, name: String, importance: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.mNotificationChannel = NotificationChannel(id, name, importance)
        }
    }

    // 채널 받음.
    fun notificationChannel(notificationChannel: () -> NotificationChannel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.mNotificationChannel = notificationChannel()
        }
    }

    fun iconID(iconID: () -> Int) {
        this.mIconID = iconID()
    }

    fun setLayoutVisible(layoutID: String, visible: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notimoteView.setLayoutVisible(
                mReceiverClass,
                mNotificationChannel.id.toString(),
                layoutID,
                visible
            )
        } else {
            notimoteView.setLayoutVisible(mReceiverClass, mChannel, layoutID, visible)
        }
    }

    fun setLayoutVisible(layoutIDs: Array<String>, visible: Int) {
        for (id in layoutIDs) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notimoteView.setLayoutVisible(
                    mReceiverClass,
                    mNotificationChannel.id.toString(),
                    id,
                    visible
                )
            } else {
                notimoteView.setLayoutVisible(mReceiverClass, mChannel, id, visible)
            }
        }
    }
}