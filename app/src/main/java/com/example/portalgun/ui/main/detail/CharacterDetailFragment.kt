package com.example.portalgun.ui.main.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.transition.TransitionInflater
import com.example.portalgun.R
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.remote.rickandmorty.episodeIds
import com.example.portalgun.ui.main.ImageLoadedCallback
import com.example.portalgun.ui.main.MainActivity
import com.example.portalgun.util.load
import timber.log.Timber
import javax.inject.Inject

/**
 * Show the details of a particular character.
 */
class CharacterDetailFragment : Fragment() {

    companion object {

        private const val ARG_CHARACTER = "character"

        fun newInstance(param1: Character) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, param1)
                }
            }
    }

    @Inject lateinit var viewModel: CharacterDetailViewModel
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
        if (savedInstanceState == null) {
            character?.episodeIds?.let { list ->
                viewModel.loadEpisodes(list)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        postponeEnterTransition()
        return inflater.inflate(R.layout.character_detail_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
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
        viewModel.episodes.observe(viewLifecycleOwner) { episodes ->
            // TODO
        }
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            val visibility = if (loading) View.VISIBLE else View.GONE
            view.findViewById<View>(R.id.progress).visibility = visibility
        }
    }
}
