package com.example.lab7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FirebaseUI()
        }
    }
}

@Composable
fun FirebaseUI() {

    val context = LocalContext.current

    val courseName = remember { mutableStateOf("") }
    val courseDuration = remember { mutableStateOf("") }
    val courseDescription = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF0F5)) // 🌸 nền hồng nhạt
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🎀 Thêm khóa học",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFE91E63) // 💖 hồng đậm
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE4EC) // 💗 hồng pastel
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                TextField(
                    value = courseName.value,
                    onValueChange = { courseName.value = it },
                    label = { Text("Tên khóa học") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = courseDuration.value,
                    onValueChange = { courseDuration.value = it },
                    label = { Text("Thời gian") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = courseDescription.value,
                    onValueChange = { courseDescription.value = it },
                    label = { Text("Mô tả") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 💖 NÚT ADD
        Button(
            onClick = {
                if (courseName.value.isEmpty()) {
                    Toast.makeText(context, "Nhập tên khóa học", Toast.LENGTH_SHORT).show()
                } else {
                    addDataToFirebase(
                        courseName.value,
                        courseDuration.value,
                        courseDescription.value,
                        context
                    ) {
                        courseName.value = ""
                        courseDuration.value = ""
                        courseDescription.value = ""

                        Toast.makeText(context, "Đã thêm!", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF69B4) // 💗 hồng đậm cute
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Add Data 💕")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🌸 NÚT VIEW
        OutlinedButton(
            onClick = {
                val intent = Intent(context, CourseDetailsActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFE91E63)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("View Courses 🌷")
        }
    }
}

fun addDataToFirebase(
    courseName: String,
    courseDuration: String,
    courseDescription: String,
    context: Context,
    onSuccess: () -> Unit
) {

    val db = FirebaseFirestore.getInstance()

    val course = hashMapOf(
        "courseName" to courseName,
        "courseDuration" to courseDuration,
        "courseDescription" to courseDescription
    )

    db.collection("Courses")
        .add(course)
        .addOnSuccessListener {
            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
            onSuccess() // 🔥 GỌI SAU KHI ADD XONG
        }
        .addOnFailureListener {
            Toast.makeText(context, "Lỗi khi thêm", Toast.LENGTH_SHORT).show()
        }
}