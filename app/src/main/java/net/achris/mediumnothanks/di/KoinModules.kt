package net.achris.mediumnothanks.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import net.achris.mediumnothanks.store.Store
import net.achris.mediumnothanks.ui.Router
import net.achris.mediumnothanks.ui.activity.ArticlesViewModel
import net.achris.mediumnothanks.ui.activity.ThemeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.*

val storeModule = module {
    single { Store(androidContext(), get()) }
    single {
        Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}

val viewModelModule = module {
    viewModel { ThemeViewModel(get()) }
    viewModel { ArticlesViewModel(get(), get()) }
}

val uiModule = module {
    single { Router() }
}

