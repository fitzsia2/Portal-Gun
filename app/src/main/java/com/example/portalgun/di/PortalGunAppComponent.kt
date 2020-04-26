package com.example.portalgun.di

import android.content.Context
import com.example.portalgun.ui.main.MainComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Top level DI Component
 */
@Singleton
@Component(modules = [AppModule::class, ViewModule::class])
interface PortalGunAppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): PortalGunAppComponent
    }

    fun mainComponent(): MainComponent.Factory
}
