package com.example.portalgun.remote.rickandmorty

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

typealias CharacterUrl = String

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    @field:Json(name = "air_date") val airDate: String,
    val episode: String,
    @field:Json(name = "characters") val characterUrls: List<CharacterUrl>,
    val url: String,
    val created: String
) : Parcelable {

    companion object {
        object DiffCallback : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }
}
