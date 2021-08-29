package br.com.marco.dioapicurrencyconverter.domain.di

import br.com.marco.dioapicurrencyconverter.domain.DeleteExchangeUseCase
import br.com.marco.dioapicurrencyconverter.domain.GetExchangeValueUseCase
import br.com.marco.dioapicurrencyconverter.domain.ListExchangeUseCase
import br.com.marco.dioapicurrencyconverter.domain.SaveExchangeUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            factory { ListExchangeUseCase(get()) }
            factory { SaveExchangeUseCase(get()) }
            factory { GetExchangeValueUseCase(get()) }
            factory { DeleteExchangeUseCase(get()) }
        }
    }
}