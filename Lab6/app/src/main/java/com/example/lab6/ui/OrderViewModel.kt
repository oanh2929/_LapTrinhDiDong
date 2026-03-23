package com.example.lab6.ui

import androidx.lifecycle.ViewModel
import com.example.lab6.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState

    fun setQuantity(qty: Int) {
        _uiState.value = _uiState.value.copy(
            quantity = qty,
            price = "$qty cupcakes"
        )
    }

    fun setFlavor(flavor: String) {
        _uiState.value = _uiState.value.copy(flavor = flavor)
    }

    fun setDate(date: String) {
        _uiState.value = _uiState.value.copy(date = date)
    }

    fun resetOrder() {
        _uiState.value = OrderUiState()
    }
}