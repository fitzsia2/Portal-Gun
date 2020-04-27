package com.example.portalgun.ui.main

import dagger.Subcomponent

@Subcomponent
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CharactersFragment)
}
