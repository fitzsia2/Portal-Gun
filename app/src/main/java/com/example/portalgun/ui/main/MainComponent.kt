package com.example.portalgun.ui.main

import com.example.portalgun.ui.main.detail.CharacterDetailFragment
import com.example.portalgun.ui.main.list.CharactersFragment
import dagger.Subcomponent

@Subcomponent
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CharactersFragment)
    fun inject(fragment: CharacterDetailFragment)
}
