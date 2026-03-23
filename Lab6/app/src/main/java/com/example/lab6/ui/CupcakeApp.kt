package com.example.lab6.ui

import android.content.Context
import androidx.compose.runtime.getValue
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

import com.example.lab6.R
import com.example.lab6.data.DataSource

enum class CupcakeScreen(@StringRes val title: Int) {
    Start(R.string.app_name),
    Flavor(R.string.choose_flavor),
    Pickup(R.string.choose_pickup_date),
    Summary(R.string.order_summary)
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel()
) {
    val navController = rememberNavController()

    val uiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {

            // START
            composable(CupcakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    }
                )
            }

            // FLAVOR
            composable(CupcakeScreen.Flavor.name) {
                val context = LocalContext.current

                SelectOptionScreen(
                    options = DataSource.flavors.map {
                        context.getString(it)
                    },
                    subtotal = uiState.price,
                    onSelectionChanged = {
                        viewModel.setFlavor(it)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Pickup.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrder(viewModel, navController)
                    }
                )
            }

            // PICKUP
            composable(CupcakeScreen.Pickup.name) {
                SelectOptionScreen(
                    options = uiState.pickupOptions,
                    subtotal = uiState.price,
                    onSelectionChanged = {
                        viewModel.setDate(it)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Summary.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrder(viewModel, navController)
                    }
                )
            }

            // SUMMARY
            composable(CupcakeScreen.Summary.name) {
                val context = LocalContext.current

                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrder(viewModel, navController)
                    },
                    onSendButtonClicked = { subject, summary ->
                        shareOrder(context, subject, summary)
                    }
                )
            }
        }
    }
}

private fun cancelOrder(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(
        CupcakeScreen.Start.name,
        inclusive = false
    )
}

private fun shareOrder(
    context: Context,
    subject: String,
    summary: String
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(intent, "Cupcake Order")
    )
}