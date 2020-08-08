package com.example.portalgun.repo

import android.net.Uri
import androidx.paging.PagingSource
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.remote.rickandmorty.RickAndMortyService
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

/**
 * [PagingSource] for loading [Character]s
 */
class RickAndMortyCharacterPagingSource @Inject constructor(
    private val service: RickAndMortyService
) : PagingSource<Int, Character>() {

    companion object {
        private const val STARTING_PAGE = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position: Int = params.key ?: STARTING_PAGE
        return try {
            val response = service.characters(position)
            val nextPageNum = response.info.next.getPageId()
            val prevPageNum = response.info.previous.getPageId()
            LoadResult.Page(
                data = response.characters,
                prevKey = prevPageNum,
                nextKey = nextPageNum
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    private fun String?.getPageId(): Int? {
        return this?.let { url ->
            Uri.parse(url).getQueryParameter("page")?.toInt()
        }
    }
}
