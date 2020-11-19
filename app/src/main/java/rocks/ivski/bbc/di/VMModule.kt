package rocks.ivski.bbc.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rocks.ivski.bbc.ui.ListVM

val viewModelModule = module {
    viewModel {
        ListVM(get(), get())
    }
}