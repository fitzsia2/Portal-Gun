package com.example.portalgun.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.portalgun.AppViewModelFactory
import com.example.portalgun.ui.main.detail.episodes.EpisodesViewModel
import com.example.portalgun.ui.main.list.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    abstract fun bindEpisodesViewModel(viewModel: EpisodesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    abstract fun bindMainViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}
