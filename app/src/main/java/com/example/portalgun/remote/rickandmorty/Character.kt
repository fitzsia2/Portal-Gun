package com.example.portalgun.remote.rickandmorty

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    @field:Json(name = "episode") val episodes: List<String>,
    val url: String,
    val created: String
) : Parcelable
