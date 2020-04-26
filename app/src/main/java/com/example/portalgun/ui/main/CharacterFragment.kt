package com.example.portalgun.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.example.portalgun.R
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.util.load
import timber.log.Timber

/**
 * Show the details of a particular character.
 */
class CharacterFragment : Fragment() {

    companion object {

        private const val ARG_CHARACTER = "character"

        fun newInstance(param1: Character) =
            CharacterFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, param1)
                }
            }
    }

    private var character: Character? = null
    private val imageLoadedCallback: ImageLoadedCallback = { loaded, image, exception ->
        if (!loaded) {
            Timber.e(exception)
        }
        startPostponedEnterTransition()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            character = it.getParcelable(ARG_CHARACTER)
        }
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        postponeEnterTransition()
        return inflater.inflate(R.layout.character_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<ImageView>(R.id.image).apply {
            load(character?.image, imageLoadedCallback)
        }
        view.findViewById<TextView>(R.id.name).text = character?.name
        view.findViewById<TextView>(R.id.gender).text = character?.gender
        view.findViewById<TextView>(R.id.location).text = character?.location?.name
        view.findViewById<TextView>(R.id.status).text = character?.status
        view.findViewById<TextView>(R.id.origin).text = character?.origin?.name
    }
}