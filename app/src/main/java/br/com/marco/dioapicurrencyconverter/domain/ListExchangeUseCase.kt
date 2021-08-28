package br.com.marco.dioapicurrencyconverter.domain

import br.com.marco.dioapicurrencyconverter.core.UseCase
import br.com.marco.dioapicurrencyconverter.data.model.ExchangeResponseValue
import br.com.marco.dioapicurrencyconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class ListExchangeUseCase(
    private val repository: CoinRepository
) : UseCase.NoParam<List<ExchangeResponseValue>>() {

    override suspend fun execute(): Flow<List<ExchangeResponseValue>> {
        return repository.list()
    }
}