package com.example.portalgun.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.portalgun.PortalGunApp
import com.example.portalgun.R

class MainActivity : AppCompatActivity() {

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (applicationContext as PortalGunApp)
            .appComponent
            .mainComponent()
            .create()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment.newInstance())
                .commitNow()
        }
    }
}
