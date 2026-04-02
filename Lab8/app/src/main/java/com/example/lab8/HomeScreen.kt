package com.example.lab8

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            // Màu vàng nền sáng hơn một chút để giống mẫu (#F6C737)
            .background(Color(0xFFF6C737)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        // Tiêu đề PIZZERIA với khoảng cách chữ rộng
        Text(
            text = "P I Z Z E R I A",
            fontSize = 42.sp,
            fontWeight = FontWeight.Black, // Đậm hơn
            color = Color(0xFFBD1111), // Màu đỏ đậm đặc trưng
            letterSpacing = 4.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Câu slogan
        Text(
            text = "Delivering\nDeliciousness right\nto your door!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 34.sp,
            color = Color(0xFF212121),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hàng dấu chấm (Carousel Indicator)
        Row {
            Box(modifier = Modifier.size(24.dp, 6.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(10.dp)))
            Spacer(modifier = Modifier.width(6.dp))
            Box(modifier = Modifier.size(24.dp, 6.dp).background(Color.Black, RoundedCornerShape(10.dp)))
            Spacer(modifier = Modifier.width(6.dp))
            Box(modifier = Modifier.size(24.dp, 6.dp).background(Color(0xFFE0E0E0), RoundedCornerShape(10.dp)))
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Nút START ORDER với đổ bóng
        Button(
            onClick = { navController.navigate("order") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBD1111)),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .width(220.dp)
                .height(55.dp)
                .shadow(8.dp, RoundedCornerShape(25.dp)) // Thêm bóng đổ
        ) {
            Text("START ORDER", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Nút SignOut (Trong mẫu có vẻ nhỏ hơn và hơi khác)
        Button(
            onClick = { navController.navigate("signin") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBD1111)),
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier
                .width(140.dp)
                .height(45.dp)
                .shadow(6.dp, RoundedCornerShape(25.dp))
        ) {
            Text("SignOut", fontSize = 16.sp)
        }

        // Đẩy ảnh xuống sát đáy màn hình
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.delivery),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth // Đảm bảo ảnh tràn chiều ngang
        )
    }
}