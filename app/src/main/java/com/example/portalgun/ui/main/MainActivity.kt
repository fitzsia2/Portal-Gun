package com.example.portalgun.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.portalgun.PortalGunApp
import com.example.portalgun.R

class MainActivity : AppCompatActivity(), CharactersFragment.ICharacterClick {

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
                .replace(R.id.container, CharactersFragment.newInstance(), "list-fragment")
                .commitNow()
        }
    }

    override val onCharacterClicked: CharacterClickListener = { character, view ->
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addSharedElement(view, "root")
            .addSharedElement(view.findViewById(R.id.image), "image")
            .replace(
                R.id.container,
                CharacterDetailFragment.newInstance(character),
                "character-fragment"
            )
            .addToBackStack(null)
            .commit()
    }
}
