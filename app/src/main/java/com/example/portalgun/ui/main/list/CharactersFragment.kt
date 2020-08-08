package com.example.portalgun.ui.main.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.portalgun.R
import com.example.portalgun.ui.main.CharacterClickListener
import com.example.portalgun.ui.main.ImageLoadedCallback
import com.example.portalgun.ui.main.MainActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CharactersFragment : Fragment() {

    interface OnCharacterClickedListener {

        val onCharacterClicked: CharacterClickListener
    }

    companion object {
        fun newInstance() = CharactersFragment()
    }

    @Inject lateinit var viewModel: CharactersViewModel
    private lateinit var layout: View
    private val pagedCharacterDataAdapter by lazy {
        PagedCharacterDataAdapter(onCharacterClicked, imageLoadedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layout = inflater.inflate(R.layout.characters_fragment, container, false)
        val characterList = layout.findViewById<RecyclerView>(R.id.character_list)
        characterList.initCharacterList()
        return layout
    }

    private fun RecyclerView.initCharacterList() {
        val layoutManager = LinearLayoutManager(context)
        this.layoutManager = layoutManager
        val recyclerDividers = getCharacterListDividers(context, layoutManager)
        addItemDecoration(recyclerDividers)
    }

    private fun getCharacterListDividers(
        context: Context,
        layoutManager: LinearLayoutManager
    ): DividerItemDecoration {
        return DividerItemDecoration(context, layoutManager.orientation).apply {
            context.getDrawable(R.drawable.character_list_divider)?.let {
                setDrawable(it)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val list = view.findViewById<RecyclerView>(R.id.character_list)
        list.adapter = pagedCharacterDataAdapter
        startCollectingCharacterStream()
    }

    private fun startCollectingCharacterStream() = lifecycleScope.launch {
        viewModel.characterStream.collect {
            pagedCharacterDataAdapter.submitData(it)
        }
    }

    private val onCharacterClicked: CharacterClickListener = { character, view ->
        (requireActivity() as? OnCharacterClickedListener)?.onCharacterClicked?.invoke(
            character,
            view
        )
    }

    private val imageLoadedCallback: ImageLoadedCallback = { loaded, _, exception ->
        if (!loaded) {
            Timber.e(exception)
        }
    }
}
