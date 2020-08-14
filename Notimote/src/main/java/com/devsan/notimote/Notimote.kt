package com.devsan.notimote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.view.View

open class Notimote {
    companion object {
        const val POWER = "notimote.power"
        const val POWER_BUTTON = "notimote.power_button"
        const val SOUND = "notimote.sound"
        const val SOUND_BUTTON_UP = "notimote.sound_up"
        const val SOUND_BUTTON_DOWN = "notimote.sound_down"
        const val CHANNEL = "notimote.channel"
        const val CHANNEL_BUTTON_UP = "notimote.channel_up"
        const val CHANNEL_BUTTON_DOWN = "notimote.channel_down"
        const val PLAYSTOP = "notimote.playstop"
        const val PLAYSTOP_BUTTON_REWIND = "notimote.playstop_rewind"
        const val PLAYSTOP_BUTTON_STOP = "notimote.playstop_stop"
        const val PLAYSTOP_BUTTON_PLAY = "notimote.playstop_play"
        const val PLAYSTOP_BUTTON_FORWARD = "notimote.playstop_forward"
        const val HOME = "notimote.home"
        const val HOME_BUTTON_EXIT = "notimote.home_exit"
        const val HOME_BUTTON_HOME = "notimote.home_home"
        const val HOME_BUTTON_BEFORE = "notimote.home_before"
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
    fun init(lambda: Notimote.() -> Unit) = this.apply(lambda).build()

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

    fun initTextPlaylist(string: () -> String){

    }

    fun setTextPlaylist(text: String) {
        notimoteView.setTextPlaylist(text)
    }

    fun setLayoutVisible(layoutID: String, visible: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notimoteView.setLayoutVisible(
                mReceiverClass,
                mNotificationChannel.id.toString(),
                Notimote.POWER,
                View.VISIBLE
            )
            notimoteView.setLayoutVisible(
                mReceiverClass,
                mNotificationChannel.id.toString(),
                layoutID,
                visible
            )
        } else {
            notimoteView.setLayoutVisible(mReceiverClass, mChannel, Notimote.POWER, View.VISIBLE)
            notimoteView.setLayoutVisible(mReceiverClass, mChannel, layoutID, visible)
        }
    }

    fun setLayoutVisible(layoutIDs: Array<String>, visible: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notimoteView.setLayoutVisible(
                mReceiverClass,
                mNotificationChannel.id.toString(),
                Notimote.POWER,
                View.VISIBLE
            )
        } else {
            notimoteView.setLayoutVisible(mReceiverClass, mChannel, Notimote.POWER, View.VISIBLE)
        }
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