package com.example.appmiercoles

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmiercoles.ui.theme.AppmiercolesTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AppmiercolesTheme {
                ContainerView()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerView() {

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.home_screen)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "I am a Snackbar! ",
                                    actionLabel = "Do something"
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.back_btn_desc
                            ),
                            tint = Color.White
                        )
                    }
                }
            )
        },

        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.bottom_app_bar),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "I am a Snackbar! ",
                            actionLabel = "Do something"
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(
                        R.string.home_fab_text
                    )
                )
            }
        }
    ) { innerPadding ->

        ContentView(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ContentView(modifier: Modifier = Modifier) {

    var counter by rememberSaveable {
        mutableIntStateOf(0)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red)
            .padding(8.dp)
    ) {

        // Imagen principal
        item {
            Image(
                painter = painterResource(id = R.drawable.papoi),
                contentDescription = "Ilustración principal de Android",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }

        // Botón de Me gusta y contador
        item {
            Row(
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = 12.dp,
                    bottom = 8.dp
                ),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.rounded_favorite_24
                    ),
                    contentDescription = "Me gusta",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            counter++
                        }
                )

                Text(
                    text = counter.toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // Nombre de la materia
        item {
            Text(
                text = stringResource(R.string.materia),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        }

        // Nombre
        item {
            Text(
                text = stringResource(R.string.nombre),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        }

        // Apellidos
        item {
            Text(
                text = stringResource(R.string.apellidos),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        }

        // Galería de imágenes horizontal
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                item {
                    Image(
                        painter = painterResource(id = R.drawable.papoi1),
                        contentDescription = "Imagen 1",
                        modifier = Modifier
                            .size(120.dp)
                    )
                }

                item {
                    Image(
                        painter = painterResource(id = R.drawable.papoi2),
                        contentDescription = "Imagen 2",
                        modifier = Modifier
                            .size(120.dp)
                    )
                }

                item {
                    Image(
                        painter = painterResource(id = R.drawable.papoi3),
                        contentDescription = "Imagen 3",
                        modifier = Modifier
                            .size(120.dp)
                    )
                }

                item {
                    Image(
                        painter = painterResource(id = R.drawable.papoi4),
                        contentDescription = "Imagen 4",
                        modifier = Modifier
                            .size(120.dp)
                    )
                }

                item {
                    Image(
                        painter = painterResource(id = R.drawable.papoi5),
                        contentDescription = "Imagen 5",
                        modifier = Modifier
                            .size(120.dp)
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    locale = "es-rMX"
)
@Composable
fun ContainerViewPreview() {
    AppmiercolesTheme {
        ContainerView()
    }
}