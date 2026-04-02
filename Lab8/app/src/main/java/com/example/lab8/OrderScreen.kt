package com.example.lab8

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lab8.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

// Data class giả lập dữ liệu món ăn
data class Pizza(val name: String, val price: String, val imageRes: Int)

@Composable
fun OrderScreen(navController: NavController) {
    val user = FirebaseAuth.getInstance().currentUser

    // Danh sách các món Pizza
    val pizzaList = listOf(
        Pizza("Margherita", "$12.00", R.drawable.pizza), // Thay bằng id ảnh thật của bạn
        Pizza("Pepperoni", "$15.50", R.drawable.pizza),
        Pizza("Hawaiian", "$14.00", R.drawable.pizza)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6C737)) // Màu vàng đồng bộ với HomeScreen
    ) {
        // --- Custom Top Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("PIZZERIA", fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color(0xFFBD1111))
                Text("Hello, ${user?.email?.split("@")?.get(0) ?: "User"}", fontSize = 14.sp, color = Color.DarkGray)
            }

            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(Screen.SignIn.route) {
                    popUpTo(0) // Xóa sạch bộ nhớ điều hướng để logout an toàn
                }
            }) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color(0xFFBD1111))
            }
        }

        // --- Danh sách món ăn ---
        Text(
            "Our Best Menu",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(pizzaList) { pizza ->
                PizzaItemCard(pizza)
            }
        }
    }
}

@Composable
fun PizzaItemCard(pizza: Pizza) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ảnh Pizza
            Image(
                painter = painterResource(id = pizza.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Thông tin món
            Column(modifier = Modifier.weight(1f)) {
                Text(pizza.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(pizza.price, fontSize = 16.sp, color = Color(0xFFBD1111), fontWeight = FontWeight.SemiBold)
            }

            // Nút thêm vào giỏ
            IconButton(
                onClick = { /* Xử lý thêm vào giỏ */ },
                modifier = Modifier.background(Color(0xFFF6C737), RoundedCornerShape(10.dp))
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.Black)
            }
        }
    }
}