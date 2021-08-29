package br.com.marco.dioapicurrencyconverter.presentation.di

import br.com.marco.dioapicurrencyconverter.presentation.HistoryViewModel
import br.com.marco.dioapicurrencyconverter.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModules())
    }

    private fun viewModelModules(): Module {
        return module {
            viewModel { HistoryViewModel(get(),get()) }
            viewModel { MainViewModel(get(), get()) }
        }
    }
}