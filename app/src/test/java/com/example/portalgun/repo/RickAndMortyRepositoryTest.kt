package com.example.portalgun.repo

import com.example.portalgun.remote.rickandmorty.RickAndMortyService
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Unit test for [RickAndMortyRepository]
 */
class RickAndMortyRepositoryTest {

    @Test
    fun `episodes makes comma separated list`() = runBlocking {
        val mockService = mockk<RickAndMortyService>()
        coJustRun { mockService.episodes("1,2,3") }
        val repoUnderTest = RickAndMortyRepository(mockService)

        repoUnderTest.episodes(listOf(1, 2, 3))

        coVerify { mockService.episodes("1,2,3") }
    }
}
