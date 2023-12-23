package com.abdullah.tourismapp.di

import com.abdullah.tourismapp.core.domain.usecase.TourismInteractor
import com.abdullah.tourismapp.core.domain.usecase.TourismUseCase
import com.abdullah.tourismapp.detail.DetailTourismViewModel
import com.abdullah.tourismapp.favorite.FavoriteViewModel
import com.abdullah.tourismapp.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<TourismUseCase> { TourismInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailTourismViewModel(get()) }
}