package rocks.ivski.bbc.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rocks.ivski.bbc.ui.list.ListVM

val viewModelModule = module {
    viewModel {
        ListVM(get(), get())
    }
}