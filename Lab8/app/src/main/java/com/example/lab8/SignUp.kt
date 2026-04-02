package com.example.lab8

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab8.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUp(navController: NavController) {

    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.pizza),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(20.dp)) {

            Text("Create Account", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(20.dp),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                navController.navigate(Screen.Order.route)
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                },
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign Up", color = Color.White)
            }
        }
    }
}