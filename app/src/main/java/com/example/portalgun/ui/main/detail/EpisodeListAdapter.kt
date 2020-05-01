package com.example.portalgun.ui.main.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.portalgun.R
import com.example.portalgun.remote.rickandmorty.Episode

class EpisodeListAdapter : ListAdapter<Episode, EpisodeListAdapter.EpisodeViewHolder>(
    Episode.Companion.DiffCallback
) {

    class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val episodeView = LayoutInflater.from(parent.context)
            .inflate(R.layout.episode_list_item, parent, false)
        return EpisodeViewHolder(episodeView)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        getItem(position).let { episode ->
            with(holder.itemView) {
                findViewById<TextView>(R.id.name).text = episode.name
                findViewById<TextView>(R.id.episode_code).text = episode.episode
            }
        }
    }
}
