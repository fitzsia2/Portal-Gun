package com.example.portalgun.ui.main.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portalgun.R
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.ui.main.CharacterClickListener
import com.example.portalgun.ui.main.ImageLoadedCallback
import com.example.portalgun.util.load

class CharacterListAdapter(
    private val characters: Array<Character>,
    private val onCharacterClicked: CharacterClickListener,
    private val onImageLoaded: ImageLoadedCallback = { _, _, _ -> }
) : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(characterView: View) : RecyclerView.ViewHolder(characterView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val characterView = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_list_item, parent, false)
        return CharacterViewHolder(characterView)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        with(holder.itemView) {
            setOnClickListener {
                onCharacterClicked(character, it)
            }
            transitionName = "card-$position"
            findViewById<ImageView>(R.id.image).apply {
                load(character.image, onImageLoaded)
                transitionName = "image-$position"
            }
            findViewById<TextView>(R.id.name).text = character.name
            findViewById<TextView>(R.id.gender).text = character.gender
            findViewById<TextView>(R.id.location).text = character.location.name
        }
    }
}
