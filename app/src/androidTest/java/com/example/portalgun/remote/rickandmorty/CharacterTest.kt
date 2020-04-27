package com.example.portalgun.remote.rickandmorty

import com.google.common.truth.Truth
import org.junit.Test

/**
 * Unit test for [Character]
 */
class CharacterTest {

    @Test
    fun episodeIds_returns_a_list_of_ids() {
        val testCharacter = Character(
            id = "1",
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Origin(
                name = "Earth (C-137)",
                url = "https://rickandmortyapi.com/api/location/1"
            ),
            location = Location(
                name = "Earth (Replacement Dimension)",
                url = "https://rickandmortyapi.com/api/location/20"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episodes = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3",
                "https://rickandmortyapi.com/api/episode/4"
            ),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        )
        Truth.assertThat(testCharacter.episodeIds).containsExactly(1, 2, 3, 4)
    }
}
