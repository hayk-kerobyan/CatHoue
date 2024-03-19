package com.example.cathouse.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.example.cathouse.BuildConfig
import com.example.cathouse.features.cats.layers.data.datasource.remote.CatApi
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val TIMEOUT = 30L

val networkModule = module {

    single<Gson> {
        Gson()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create(get())
    }

    single {
        OkHttpClient.Builder()
            .callTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }

    single<CatApi> {
        get<Retrofit>().create(CatApi::class.java)
    }

    single {
        MemoryCache.Builder(get())
            .maxSizePercent(0.25)
            .build()
    }

    single {
        DiskCache.Builder()
            .directory(get<Context>().cacheDir.resolve("image_cache"))
            .maxSizePercent(0.25)
            .build()
    }

    single {
        ImageLoader.Builder(get())
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCache(get<MemoryCache>())
            .respectCacheHeaders(false)
            .diskCache(get<DiskCache>())
            .build()
    }
}