package net.achris.mediumnothanks.di

import net.achris.mediumnothanks.ui.activity.ActivityViewModel
import net.achris.mediumnothanks.store.Store
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val storeModule = module {
    single { Store(androidContext()) }
}

val viewModelModule = module {
    viewModel { ActivityViewModel(get()) }
}