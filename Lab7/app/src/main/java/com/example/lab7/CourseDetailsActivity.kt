package com.example.lab7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

class CourseDetailsActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("") }
                        )
                    }
                ) { padding ->

                    val courseList = remember { mutableStateListOf<Course?>() }
                    val db = FirebaseFirestore.getInstance()

                    // 🔥 LOAD DATA
                    LaunchedEffect(true) {
                        db.collection("Courses")
                            .get()
                            .addOnSuccessListener { result ->
                                courseList.clear()
                                if (!result.isEmpty) {
                                    for (doc in result.documents) {
                                        val course = doc.toObject(Course::class.java)
                                        course?.courseID = doc.id
                                        courseList.add(course)
                                    }
                                } else {
                                    Toast.makeText(
                                        this@CourseDetailsActivity,
                                        "Không có dữ liệu",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    this@CourseDetailsActivity,
                                    "Lỗi load dữ liệu",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }

                    CourseListUI(
                        context = LocalContext.current,
                        courseList = courseList,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

@Composable
fun CourseListUI(
    context: Context,
    courseList: SnapshotStateList<Course?>,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF0F5)) // 🌸 nền hồng nhạt
    ) {

        Text(
            text = "📚 Danh sách khóa học",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFE91E63),
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn {

            itemsIndexed(courseList) { _, item ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFE4EC) // 💗 card hồng
                    ),
                    elevation = CardDefaults.cardElevation(6.dp),
                    onClick = {
                        val intent = Intent(context, UpdateCourse::class.java)

                        intent.putExtra("courseID", item?.courseID)
                        intent.putExtra("courseName", item?.courseName)
                        intent.putExtra("courseDuration", item?.courseDuration)
                        intent.putExtra("courseDescription", item?.courseDescription)

                        context.startActivity(intent)
                    }
                ) {

                    Column(modifier = Modifier.padding(12.dp)) {

                        Text(
                            text = item?.courseName ?: "",
                            color = Color(0xFFE91E63),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "⏰ ${item?.courseDuration}",
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "📝 ${item?.courseDescription}",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

// 🔥 HÀM DELETE
fun deleteCourse(
    courseID: String,
    context: Context,
    courseList: SnapshotStateList<Course?>
) {
    val db = FirebaseFirestore.getInstance()

    db.collection("Courses")
        .document(courseID)
        .delete()
        .addOnSuccessListener {
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()

            // 👉 Xóa luôn trên UI (không cần reload)
            courseList.removeIf { it?.courseID == courseID }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
        }
}