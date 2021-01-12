package com.mibrahimuadev.cikasodevelopertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.mainToolbar))
        supportActionBar?.setDisplayShowCustomEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}