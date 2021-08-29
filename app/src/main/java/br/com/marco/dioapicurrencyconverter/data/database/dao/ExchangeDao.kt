package br.com.marco.dioapicurrencyconverter.data.database.dao

import androidx.room.*
import br.com.marco.dioapicurrencyconverter.data.model.ExchangeResponseValue
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeDao {

    @Query("SELECT * FROM tb_exchange")
    fun findAll(): Flow<List<ExchangeResponseValue>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: ExchangeResponseValue)

    @Delete
    suspend fun delete(entity: ExchangeResponseValue)

}