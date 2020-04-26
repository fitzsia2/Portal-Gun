package com.example.portalgun.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portalgun.R
import com.example.portalgun.remote.rickandmorty.Character
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CharacterListAdapter(
    private val characters: Array<Character>,
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
            findViewById<ImageView>(R.id.image).load(character.image, onImageLoaded)
            findViewById<TextView>(R.id.name).text = character.name
            findViewById<TextView>(R.id.gender).text = character.gender
            findViewById<TextView>(R.id.location).text = character.location.name
        }
    }

    private fun ImageView.load(path: String, onLoaded: ImageLoadedCallback = { _, _, _ -> }) {
        Picasso.get().load(path).into(this, object : Callback {

            override fun onSuccess() = onLoaded(true, this@load, null)

            override fun onError(e: Exception?) = onLoaded(false, this@load, e)
        })
    }
}
