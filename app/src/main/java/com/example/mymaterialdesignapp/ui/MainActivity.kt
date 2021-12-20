package com.example.mymaterialdesignapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymaterialdesignapp.R
import com.example.mymaterialdesignapp.ui.fragments.POTDFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, POTDFragment.newInstance())
                .commitNow()
        }
    }
}