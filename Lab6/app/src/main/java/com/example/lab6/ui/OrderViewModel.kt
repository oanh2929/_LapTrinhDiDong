package com.example.lab6.ui

import androidx.lifecycle.ViewModel
import com.example.lab6.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.NumberFormat

private const val PRICE_PER_CUPCAKE = 2.0
private const val SAME_DAY_PRICE = 3.0

class OrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState

    fun setQuantity(qty: Int) {
        updatePrice(qty, _uiState.value.date)
        _uiState.value = _uiState.value.copy(quantity = qty)
    }

    fun setFlavor(flavor: String) {
        _uiState.value = _uiState.value.copy(flavor = flavor)
    }

    fun setDate(date: String) {
        updatePrice(_uiState.value.quantity, date)
        _uiState.value = _uiState.value.copy(date = date)
    }

    private fun updatePrice(qty: Int, date: String) {
        var price = qty * PRICE_PER_CUPCAKE

        // nếu chọn ngày đầu tiên thì cộng thêm phí
        if (date.isNotEmpty()) {
            price += SAME_DAY_PRICE
        }

        val formattedPrice = NumberFormat.getCurrencyInstance().format(price)

        _uiState.value = _uiState.value.copy(price = formattedPrice)
    }

    fun resetOrder() {
        _uiState.value = OrderUiState()
    }
}