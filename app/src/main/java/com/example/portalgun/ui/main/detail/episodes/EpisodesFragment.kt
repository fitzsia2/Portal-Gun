package com.example.portalgun.ui.main.detail.episodes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.portalgun.R
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.remote.rickandmorty.episodeIds
import com.example.portalgun.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * Fragment for displaying a list of episodes based on a [Character]
 */
class EpisodesFragment : Fragment() {

    @Inject lateinit var viewModel: EpisodesViewModel
    lateinit var layout: View
    private val episodeListAdapter = EpisodeListAdapter()
    private var character: Character? = null

    companion object {

        private const val ARG_CHARACTER = "character"

        fun newInstance(param1: Character) =
            EpisodesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHARACTER, param1)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            character = it.getParcelable(ARG_CHARACTER)
        }
        if (savedInstanceState == null) {
            character?.episodeIds?.let { list ->
                viewModel.loadEpisodes(list)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.episodes_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list: RecyclerView = view.findViewById(R.id.episodes)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = episodeListAdapter

        viewModel.episodes.observe(viewLifecycleOwner) { episodes ->
            list.visibility = View.VISIBLE
            episodeListAdapter.submitList(episodes)
        }
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            val visibility = if (loading) View.VISIBLE else View.GONE
            view.findViewById<View>(R.id.progress).visibility = visibility
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error.message?.let { message ->
                Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
