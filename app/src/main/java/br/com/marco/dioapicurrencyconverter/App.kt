package br.com.marco.dioapicurrencyconverter

import android.app.Application
import br.com.marco.dioapicurrencyconverter.data.di.DataModules
import br.com.marco.dioapicurrencyconverter.domain.di.DomainModule
import br.com.marco.dioapicurrencyconverter.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        DataModules.load()
        DomainModule.load()
        PresentationModule.load()
    }
}