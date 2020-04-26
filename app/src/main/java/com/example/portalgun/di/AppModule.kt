package com.example.portalgun.di

import com.example.portalgun.remote.rickandmorty.RickAndMortyService
import com.example.portalgun.repo.IRickAndMortyRepository
import com.example.portalgun.repo.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideRickAndMortyService(): RickAndMortyService {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor())
                    .build()
            )
            .build()
            .create(RickAndMortyService::class.java)
    }

    @Singleton
    @Provides
    fun provideRickAndMortyRepository(
        rickAndMortyService: RickAndMortyService
    ): IRickAndMortyRepository {
        return RickAndMortyRepository(rickAndMortyService)
    }
}
