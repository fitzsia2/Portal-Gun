package com.example.portalgun

import android.app.Application
import com.example.portalgun.di.DaggerPortalGunAppComponent
import com.example.portalgun.di.PortalGunAppComponent
import timber.log.Timber

class PortalGunApp : Application() {

    open val appComponent: PortalGunAppComponent by lazy {
        DaggerPortalGunAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
