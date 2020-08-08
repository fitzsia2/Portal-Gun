package com.example.portalgun.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.portalgun.R
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.ui.main.CharacterClickListener
import com.example.portalgun.ui.main.ImageLoadedCallback

/**
 * [PagingDataAdapter] for showing [Character]s
 */
class PagedCharacterDataAdapter(
    private val onCharacterClicked: CharacterClickListener,
    private val onImageLoaded: ImageLoadedCallback = { _, _, _ -> }
) : PagingDataAdapter<Character, CharacterViewHolder>(CHARACTER_COMPARATOR) {

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val characterView = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        return CharacterViewHolder(
            characterView,
            onCharacterClicked,
            onImageLoaded
        )
    }


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        if (character == null) {
            holder.bindPlaceholder()
        } else {
            holder.bind(character)
        }
    }
}
