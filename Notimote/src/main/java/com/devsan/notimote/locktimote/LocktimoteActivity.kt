package com.devsan.notimote.locktimote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.devsan.notimote.R

class LocktimoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
            this.setShowWhenLocked(true)
        }else{
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_locktimote)
    }
}