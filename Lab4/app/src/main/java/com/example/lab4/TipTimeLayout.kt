package com.example.lab4

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TipTimeLayout() {

    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(amount, tipPercent, roundUp)

    Column(
        modifier = Modifier
            .padding(40.dp)
            .fillMaxWidth()
    ) {

        Text(text = "Calculate Tip")

        Spacer(modifier = Modifier.height(16.dp))

        EditNumberField(
            label = "Bill Amount",
            value = amountInput,
            onValueChanged = { amountInput = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        EditNumberField(
            label = "Tip %",
            value = tipInput,
            onValueChanged = { tipInput = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Tip Amount: $tip")
    }
}