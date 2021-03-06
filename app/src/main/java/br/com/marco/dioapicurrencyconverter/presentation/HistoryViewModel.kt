package br.com.marco.dioapicurrencyconverter.presentation

import androidx.lifecycle.*
import br.com.marco.dioapicurrencyconverter.data.model.ExchangeResponseValue
import br.com.marco.dioapicurrencyconverter.domain.DeleteExchangeUseCase
import br.com.marco.dioapicurrencyconverter.domain.ListExchangeUseCase
import br.com.marco.dioapicurrencyconverter.domain.SaveExchangeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val listExchangeUseCase: ListExchangeUseCase,
    private val deleteExchangeUseCase: DeleteExchangeUseCase

) : ViewModel(), LifecycleObserver {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getExchanges() {
        viewModelScope.launch {
            listExchangeUseCase()
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Success(it)
                }
        }
    }

    fun deleteExchange(exchange: ExchangeResponseValue) {
        viewModelScope.launch {
            deleteExchangeUseCase(exchange)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Deleted
                }
        }
    }



    sealed class State {
        object Loading : State()
        object Deleted : State()
        data class Success(val list: List<ExchangeResponseValue>) : State()
        data class Error(val error: Throwable) : State()
    }
}