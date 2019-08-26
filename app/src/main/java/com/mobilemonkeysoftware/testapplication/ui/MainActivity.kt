package com.mobilemonkeysoftware.testapplication.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mobilemonkeysoftware.testapplication.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navigateTo(this, NavigationDirection.LIST)
    }

    override fun onBackPressed() {
        manageStack()
    }
}

