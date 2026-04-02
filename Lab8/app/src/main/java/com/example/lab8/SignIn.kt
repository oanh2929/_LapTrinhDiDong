package com.example.lab8

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SignIn(navController: NavController) {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Màu sắc theo ảnh mẫu
    val primaryRed = Color(0xFFBD1111)
    val textFieldBorder = Color(0xFFBD1111).copy(alpha = 0.5f)
    val backgroundColor = Color(0xFFFFFBFA) // Màu nền kem nhạt

    // Tạo hình dạng lượn sóng (Wavy) cho ảnh Pizza
    val wavyShape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, size.height * 0.82f)
        // Đường cong lượn sóng phức tạp một chút để giống ảnh
        quadraticBezierTo(
            size.width * 0.75f, size.height * 0.75f,
            size.width * 0.5f, size.height * 0.9f
        )
        quadraticBezierTo(
            size.width * 0.25f, size.height * 1.05f,
            0f, size.height * 0.85f
        )
        close()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Ảnh Pizza với đường cắt lượn sóng chuyên nghiệp
        Image(
            painter = painterResource(id = R.drawable.pizza),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
                .clip(wavyShape)
        )

        Spacer(modifier = Modifier.height(35.dp))

        // 2. Tiêu đề (Giữ chữ "Wellcome" 2 chữ l theo mẫu)
        Row {
            Text(
                text = "Wellcome Back ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "PIZZERIA!",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = primaryRed
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 3. Ô nhập Email với viền đỏ nhạt
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email", color = textFieldBorder) },
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textFieldBorder,
                focusedBorderColor = primaryRed,
                cursorColor = primaryRed
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        // 4. Ô nhập Password với icon khóa đỏ
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Password", color = textFieldBorder) },
            visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    // Bạn cần thêm icon khóa vào res/drawable/ic_lock.xml
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = null,
                    tint = primaryRed,
                    modifier = Modifier.size(22.dp)
                )
            },
            shape = RoundedCornerShape(15.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textFieldBorder,
                focusedBorderColor = primaryRed
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.height(35.dp))

        // 5. Nút Sign In có hiệu ứng đổ bóng
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    navController.navigate("home") {
                        popUpTo("signin") { inclusive = true }
                    }
                } else {
                    Toast.makeText(context, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = primaryRed),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(55.dp)
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(15.dp))
        ) {
            Text("Sign In", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(25.dp))

        // 6. Link đăng ký
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Is it first for you? ", fontSize = 14.sp, color = Color.Black)
            Text(
                text = "Sign Up now!",
                fontSize = 14.sp,
                color = primaryRed,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text("OR Sign In with", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(15.dp))

        // 7. Social Buttons (Google & Facebook)
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialCircleIcon(R.drawable.ic_google)
            SocialCircleIcon(R.drawable.ic_facebook)
        }
    }
}

@Composable
fun SocialCircleIcon(iconRes: Int) {
    Surface(
        modifier = Modifier.size(50.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        color = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}