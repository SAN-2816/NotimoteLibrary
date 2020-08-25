package com.devsan.notimote.locktimote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.devsan.notimote.R
import com.devsan.notimote.databinding.ActivityLocktimoteBinding

class LocktimoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocktimoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_locktimote)
        binding.activityLocktimote = this

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
            this.setShowWhenLocked(true)
        }else{
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_locktimote)
        binding.locktimoteTextView.text = "asd"
    }
}