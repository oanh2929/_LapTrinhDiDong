package com.example.lab2.ui.theme



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab2.R


@Composable
fun ProfileCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDFF1E1)), // xanh nhạt giống hình
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        // Logo + tên
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.android_logo),
                contentDescription = "Android logo",
                modifier = Modifier.size(96.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Jennifer Doe",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF073042)
            )

            Text(
                text = "Android Developer Extraordinaire",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF3DDC84)
            )
        }

        // Thông tin liên hệ
        Column(
            modifier = Modifier.padding(bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            InfoRow(Icons.Default.Call, "+11 (123) 444 555 666")
            InfoRow(Icons.Default.Share, "@AndroidDev")
            InfoRow(Icons.Default.Email, "jen.doe@android.com")
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF073042)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color(0xFF073042)
        )
    }
}
