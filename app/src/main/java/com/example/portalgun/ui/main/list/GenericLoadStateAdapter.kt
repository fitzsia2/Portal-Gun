package com.example.portalgun.ui.main.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.portalgun.R
import kotlinx.android.synthetic.main.character_loading_list_item.view.*
import kotlinx.android.synthetic.main.error_layout.view.*

/**
 * [LoadStateAdapter] for displaying progress and error messages with a retry button
 */
class GenericLoadStateAdapter(
    private val retry: RetryClickHandler
) : LoadStateAdapter<GenericLoadStateAdapter.CharacterLoadStateViewHolder>() {

    class CharacterLoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: CharacterLoadStateViewHolder, loadState: LoadState) {
        val progress = holder.itemView.load_state_progress
        val btnRetry = holder.itemView.error_layout_button
        val txtErrorMessage = holder.itemView.error_layout_text

        btnRetry.isVisible = loadState !is LoadState.Loading
        txtErrorMessage.isVisible = loadState !is LoadState.Loading
        progress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            txtErrorMessage.text = loadState.error.localizedMessage
        }

        btnRetry.setOnClickListener { retry() }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharacterLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_loading_list_item, parent, false)
        return CharacterLoadStateViewHolder(view)
    }
}

typealias RetryClickHandler = () -> Unit
