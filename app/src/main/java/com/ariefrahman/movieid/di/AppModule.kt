package com.ariefrahman.movieid.di

import com.ariefrahman.core.domain.usecase.MovieInteractor
import com.ariefrahman.core.domain.usecase.MovieUsecase
import com.ariefrahman.movieid.ui.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUsecase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel{ MovieViewModel(get()) }

}