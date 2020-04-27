package com.example.portalgun.ui.main.list

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
import com.example.portalgun.ui.main.CharacterClickListener
import com.example.portalgun.ui.main.ImageLoadedCallback
import com.example.portalgun.ui.main.MainActivity
import timber.log.Timber
import javax.inject.Inject

class CharactersFragment : Fragment() {

    interface ICharacterClick {

        val onCharacterClicked: CharacterClickListener
    }

    companion object {
        fun newInstance() =
            CharactersFragment()
    }

    @Inject lateinit var viewModel: MainViewModel
    lateinit var layout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.characters_fragment, container, false)
            .also { layout = it }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = view.findViewById<RecyclerView>(R.id.character_list)
        list.layoutManager = LinearLayoutManager(context)
        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            list.adapter =
                CharacterListAdapter(
                    characters.toTypedArray(),
                    onCharacterClicked,
                    imageLoadedCallback
                )
        }
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            val visibility = if (loading) View.VISIBLE else View.GONE
            view.findViewById<View>(R.id.progress).visibility = visibility
        }
    }

    private val onCharacterClicked: CharacterClickListener = { character, view ->
        (requireActivity() as? ICharacterClick)?.onCharacterClicked?.invoke(character, view)
    }

    private val imageLoadedCallback: ImageLoadedCallback = { loaded, image, exception ->
        if (!loaded) {
            Timber.e(exception)
        }
    }
}