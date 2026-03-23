package com.example.lab6.ui



import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab6.data.OrderUiState

@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    val summary = """
        Quantity: ${orderUiState.quantity}
        Flavor: ${orderUiState.flavor}
        Pickup Date: ${orderUiState.date}
        Total: ${orderUiState.price}
    """.trimIndent()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Order Summary", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(summary)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSendButtonClicked("Cupcake Order", summary)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onCancelButtonClicked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}