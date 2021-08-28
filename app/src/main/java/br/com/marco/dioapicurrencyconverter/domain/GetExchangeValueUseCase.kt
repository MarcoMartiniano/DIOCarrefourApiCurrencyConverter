package br.com.marco.dioapicurrencyconverter.domain

import br.com.marco.dioapicurrencyconverter.core.UseCase
import br.com.marco.dioapicurrencyconverter.data.model.ExchangeResponseValue
import br.com.marco.dioapicurrencyconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class GetExchangeValueUseCase(
    private val repository: CoinRepository
) : UseCase<String, ExchangeResponseValue>() {

    override suspend fun execute(param: String): Flow<ExchangeResponseValue> {
        return repository.getExchangeValue(param)
    }

}