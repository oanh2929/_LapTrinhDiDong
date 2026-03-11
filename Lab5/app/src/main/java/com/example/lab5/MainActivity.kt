package com.example.lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lab5.data.Dog
import com.example.lab5.data.dogs
import com.example.lab5.ui.theme.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WoofTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    WoofApp()
                }
            }
        }
    }
}

@Composable
fun WoofApp() {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        WoofTopAppBar()

        DogList(
            dogs = dogs,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun WoofTopAppBar() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(R.drawable.ic_woof_logo),
            contentDescription = "Woof logo",
            modifier = Modifier.size(48.dp)
        )

        Text(
            text = "Woof",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun DogList(
    dogs: List<Dog>,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {

        items(dogs) { dog ->
            DogItem(dog)
        }
    }
}

@Composable
fun DogItem(dog: Dog) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(dog.imageResourceId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {

                    Text(
                        text = stringResource(dog.name),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "${dog.age} years old"
                    )
                }

                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExpandMore,
                        contentDescription = "expand"
                    )
                }
            }

            if (expanded) {

                Text(
                    text = stringResource(dog.hobbies),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}