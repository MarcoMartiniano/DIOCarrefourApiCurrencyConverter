package br.com.marco.dioapicurrencyconverter.data.repository

import br.com.marco.dioapicurrencyconverter.data.model.ExchangeResponseValue
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getExchangeValue(coins: String): Flow<ExchangeResponseValue>
    suspend fun delete(exchange: ExchangeResponseValue)
    suspend fun save(exchange: ExchangeResponseValue)
    fun list(): Flow<List<ExchangeResponseValue>>
}