package com.example.portalgun.ui.main.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.portalgun.R
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.ui.main.CharacterClickListener
import com.example.portalgun.ui.main.ImageLoadedCallback
import com.example.portalgun.util.load

/**
 * [PagingDataAdapter] for showing [Character]s
 */
class PagedCharacterDataAdapter(
    private val onCharacterClicked: CharacterClickListener,
    private val onImageLoaded: ImageLoadedCallback = { _, _, _ -> }
) :
    PagingDataAdapter<Character, PagedCharacterDataAdapter.CharacterViewHolder>(CHARACTER_COMPARATOR) {

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

    class CharacterViewHolder(
        characterView: View,
        private val onCharacterClicked: CharacterClickListener,
        private val onImageLoaded: ImageLoadedCallback = { _, _, _ -> }
    ) : RecyclerView.ViewHolder(characterView) {

        private val nameTextView = characterView.findViewById<TextView>(R.id.name)
        private val genderTextView = characterView.findViewById<TextView>(R.id.gender)
        private val locationTextView = characterView.findViewById<TextView>(R.id.location)
        private val characterImageView = characterView.findViewById<ImageView>(R.id.image)

        fun bind(character: Character) {
            with(itemView) {
                setOnClickListener {
                    onCharacterClicked(character, it)
                }
                transitionName = "card-$bindingAdapterPosition"
                characterImageView.apply {
                    load(character.image, onImageLoaded)
                    transitionName = "image-$bindingAdapterPosition"
                }
                nameTextView.text = character.name
                genderTextView.text = character.gender
                locationTextView.text = character.location.name
            }
        }

        fun bindPlaceholder() {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val characterView = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        return CharacterViewHolder(characterView, onCharacterClicked, onImageLoaded)
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
