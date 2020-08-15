package com.example.portalgun.ui.main.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
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
        PagedCharacterDataAdapter(onCharacterClicked, imageLoadedCallback).apply {
            addLoadStateListener(::handleCharacterLoadState)
        }
    }

    private fun handleCharacterLoadState(loadState: CombinedLoadStates) {
        view?.findViewById<View>(R.id.progress)?.isVisible = loadState.refresh is LoadState.Loading
        view?.findViewById<Button>(R.id.error_layout_button)?.isVisible =
            loadState.refresh is LoadState.Error
        view?.findViewById<TextView>(R.id.error_layout_text)?.handleErrorText(loadState)
    }

    private fun TextView.handleErrorText(loadState: CombinedLoadStates) {
        isVisible = loadState.refresh is LoadState.Error
        if (loadState.refresh !is LoadState.Loading) {
            // getting the error
            val error = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            error?.let {
                text = it.error.message
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layout = inflater.inflate(R.layout.characters_fragment, container, false)
        layout.findViewById<Button>(R.id.error_layout_button).apply {
            setOnClickListener { retry() }
            isVisible = false
        }
        layout.findViewById<Button>(R.id.error_layout_text).isVisible = false
        layout.findViewById<Button>(R.id.error_layout_button).setOnClickListener { retry() }
        layout.findViewById<RecyclerView>(R.id.character_list).initCharacterList()
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
            .withLoadStateHeaderAndFooter(
                GenericLoadStateAdapter(::retry),
                GenericLoadStateAdapter(::retry)
            )
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

    private fun retry() {
        pagedCharacterDataAdapter.retry()
    }

    private val imageLoadedCallback: ImageLoadedCallback = { loaded, _, exception ->
        if (!loaded) {
            Timber.e(exception)
        }
    }
}
