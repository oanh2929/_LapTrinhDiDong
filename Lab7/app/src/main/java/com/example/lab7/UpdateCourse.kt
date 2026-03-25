package com.example.lab7

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

class UpdateCourse : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val courseID = intent.getStringExtra("courseID")
        val courseName = intent.getStringExtra("courseName")
        val courseDuration = intent.getStringExtra("courseDuration")
        val courseDescription = intent.getStringExtra("courseDescription")

        setContent {
            UpdateUI(
                courseID,
                courseName,
                courseDuration,
                courseDescription
            )
        }
    }
}

@Composable
fun UpdateUI(
    courseID: String?,
    oldName: String?,
    oldDuration: String?,
    oldDescription: String?
) {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    val name = remember { mutableStateOf(oldName ?: "") }
    val duration = remember { mutableStateOf(oldDuration ?: "") }
    val description = remember { mutableStateOf(oldDescription ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF0F5))
            .padding(16.dp)
    ) {
        Spacer (modifier = Modifier.height(20.dp))

        Text(
            "✏️ Cập nhật khóa học",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFE91E63)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFE4EC) // 💗 card hồng
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Tên khóa học") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = duration.value,
                    onValueChange = { duration.value = it },
                    label = { Text("Thời gian") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text("Mô tả") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 💖 UPDATE
        Button(
            onClick = {

                if (TextUtils.isEmpty(name.value)) {
                    Toast.makeText(context, "Nhập tên", Toast.LENGTH_SHORT).show()
                } else {
                    val course = hashMapOf(
                        "courseName" to name.value,
                        "courseDuration" to duration.value,
                        "courseDescription" to description.value
                    )

                    db.collection("Courses")
                        .document(courseID ?: "")
                        .set(course)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Update thành công 💕", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Update lỗi", Toast.LENGTH_SHORT).show()
                        }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF69B4) // 💗 hồng đậm
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update 💕")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ❌ DELETE
        Button(
            onClick = {
                db.collection("Courses")
                    .document(courseID ?: "")
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(context, "Đã xóa 🗑️", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Xóa lỗi", Toast.LENGTH_SHORT).show()
                    }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete ❌")
        }
    }
}