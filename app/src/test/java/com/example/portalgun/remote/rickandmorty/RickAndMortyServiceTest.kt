package com.example.portalgun.remote.rickandmorty

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.portalgun.enqueueResponse
import com.google.common.truth.Truth
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RickAndMortyServiceTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()
    private lateinit var serviceUnderTest: RickAndMortyService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        serviceUnderTest = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor())
                    .build()
            )
            .build()
            .create(RickAndMortyService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get first page returns first page`() = runBlocking {
        mockWebServer.enqueueResponse(
            this@RickAndMortyServiceTest.javaClass.classLoader!!,
            "character.json"
        )
        try {
            val response = serviceUnderTest.characters()
            val info = response.info
            val characters = response.characters
            Truth.assertThat(info)
                .isEqualTo(Info(493, 25, "https://rickandmortyapi.com/api/character/?page=2", null))
            Truth.assertThat(characters.find { it.id == "1" })
                .isEqualTo(
                    Character(
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
                        episodes = listOf("https://rickandmortyapi.com/api/episode/1"),
                        url = "https://rickandmortyapi.com/api/character/1",
                        created = "2017-11-04T18:48:46.250Z"
                    )
                )
        } catch (e: Exception) {
            fail(e.message)
        }
    }

    @Test
    fun `episodes returns a list of episodes`() = runBlocking {
        val episode10 = Episode(
            id = 10,
            name = "Close Rick-counters of the Rick Kind",
            airDate = "April 7, 2014",
            episode = "S01E10",
            characterUrls = listOf("https://rickandmortyapi.com/api/character/1"),
            url = "https://rickandmortyapi.com/api/episode/10",
            created = "2017-11-10T12:56:34.747Z"
        )
        val episode28 = Episode(
            id = 28,
            name = "The Ricklantis Mixup",
            airDate = "September 10, 2017",
            episode = "S03E07",
            characterUrls = listOf("https://rickandmortyapi.com/api/character/1"),
            url = "https://rickandmortyapi.com/api/episode/28",
            created = "2017-11-10T12:56:36.618Z"
        )
        mockWebServer.enqueueResponse(
            this@RickAndMortyServiceTest.javaClass.classLoader!!,
            "episodes.json"
        )
        try {
            val episodes = serviceUnderTest.episodes("10,28")
            Truth.assertThat(episodes)
                .contains(episode10)
            Truth.assertThat(episodes)
                .contains(episode28)
        } catch (e: Exception) {
            fail(e.message)
        }
    }
}
