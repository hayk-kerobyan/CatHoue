package com.example.cathouse.features.cats.di

import com.example.cathouse.db.AppDatabase
import com.example.cathouse.features.cats.layers.data.datasource.local.CatDao
import com.example.cathouse.features.cats.layers.data.repo.CatRepoImpl
import com.example.cathouse.features.cats.layers.domain.repo.CatRepo
import com.example.cathouse.features.cats.layers.presenter.details.CatDetailsViewModel
import com.example.cathouse.features.cats.layers.presenter.list.CatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val catsModule = module {

    single<CatDao> {
        get<AppDatabase>().catDao()
    }

    single<CatRepo> {
        CatRepoImpl(get(), get(), get(), get())
    }

    viewModel {
        CatsViewModel(get())
    }

    viewModel {
        CatDetailsViewModel(get(), get())
    }
}