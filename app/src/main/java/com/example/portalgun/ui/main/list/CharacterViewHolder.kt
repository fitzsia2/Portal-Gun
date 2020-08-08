package com.example.portalgun.ui.main.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portalgun.R
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.ui.main.CharacterClickListener
import com.example.portalgun.ui.main.ImageLoadedCallback
import com.example.portalgun.util.load

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
            setOnClickListener { onCharacterClicked(character, it) }
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
