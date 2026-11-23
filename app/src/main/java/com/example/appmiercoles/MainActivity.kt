
package com.example.appmiercoles

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope

import com.example.appmiercoles.ui.theme.AppmiercolesTheme

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        splashScreen.setKeepOnScreenCondition {
            keepSplash
        }

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(3000)
            keepSplash = false
        }

        ShowToast("Función onCreate()")

        enableEdgeToEdge()

        setContent {
            AppmiercolesTheme {
                ContainerView()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        ShowToast("Función onStart()")
    }

    override fun onResume() {
        super.onResume()
        ShowToast("Función onResume()")
    }

    override fun onPause() {
        ShowToast("Función onPause()")
        super.onPause()
    }

    override fun onStop() {
        ShowToast("Función onStop()")
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
        ShowToast("Función onRestart()")
    }

    override fun onDestroy() {
        ShowToast("Función onDestroy()")
        super.onDestroy()
    }

    private fun ShowToast(message: String) {

        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_SHORT
        ).show()

        Log.i(TAG, message)
    }
}


// --------------------------------------------------
// CONTENEDOR PRINCIPAL
// --------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerView() {

    val context = LocalContext.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    // Estado del menú lateral
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    // --------------------------------------------------
    // CÁMARA
    // --------------------------------------------------

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->

        if (bitmap != null) {

            Toast.makeText(
                context,
                "¡Foto tomada correctamente!",
                Toast.LENGTH_SHORT
            ).show()

        } else {

            Toast.makeText(
                context,
                "No se tomó ninguna fotografía",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Solicitar permiso de cámara

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (isGranted) {

            cameraLauncher.launch(null)

        } else {

            Toast.makeText(
                context,
                "Se necesita permiso para utilizar la cámara",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    // --------------------------------------------------
    // MENÚ LATERAL
    // --------------------------------------------------

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Menú principal",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider()
                }


                // INICIO

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                    },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )


                // PERFIL

                NavigationDrawerItem(
                    label = {
                        Text(stringResource(R.string.profile))
                    },
                    selected = false,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        Toast.makeText(
                            context,
                            "Opción Perfil",
                            Toast.LENGTH_SHORT
                        ).show()

                    },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )


                // CONFIGURACIÓN

                NavigationDrawerItem(
                    label = {
                        Text(stringResource(R.string.config))
                    },
                    selected = false,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        Toast.makeText(
                            context,
                            "Opción Configuración",
                            Toast.LENGTH_SHORT
                        ).show()

                    },
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )


                // WHATSAPP

                NavigationDrawerItem(
                    label = {
                        Text("WhatsApp")
                    },
                    selected = false,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        openWhatsApp(context)

                    },
                    icon = {
                        Icon(
                            Icons.Default.Message,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )


                // FACEBOOK

                NavigationDrawerItem(
                    label = {
                        Text("Facebook")
                    },
                    selected = false,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        openFacebook(context)

                    },
                    icon = {
                        Icon(
                            Icons.Default.ThumbUp,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )


                // CÁMARA

                NavigationDrawerItem(
                    label = {
                        Text("Cámara")
                    },
                    selected = false,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        permissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )

                    },
                    icon = {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )


                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )


                // CERRAR SESIÓN

                NavigationDrawerItem(
                    label = {
                        Text(stringResource(R.string.logout))
                    },
                    selected = false,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        Toast.makeText(
                            context,
                            "Cerrar sesión",
                            Toast.LENGTH_SHORT
                        ).show()

                    },
                    icon = {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }

    ) {

        // --------------------------------------------------
        // SCAFFOLD
        // --------------------------------------------------

        Scaffold(

            modifier = Modifier.fillMaxSize(),

            // BARRA SUPERIOR

            topBar = {

                TopAppBar(

                    title = {

                        Text(
                            text = stringResource(R.string.home_screen)
                        )
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = Color.White
                    ),

                    // BOTÓN MENÚ LATERAL

                    navigationIcon = {

                        IconButton(
                            onClick = {

                                scope.launch {

                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }

                                }

                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }
                    },

                    // BOTONES DE LA DERECHA

                    actions = {

                        // ABRIR PÁGINA WEB

                        IconButton(
                            onClick = {
                                openWebScreen(context)
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.AddLink,
                                contentDescription = "Abrir sitio web",
                                tint = Color.White
                            )
                        }


                        // ABRIR SECOND ACTIVITY

                        IconButton(
                            onClick = {

                                val intent = Intent(
                                    context,
                                    SecondActivity::class.java
                                )

                                context.startActivity(intent)

                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir",
                                tint = Color.White
                            )
                        }


                        // MENÚ DE TRES PUNTOS

                        DropDownMenu()

                    }
                )
            },


            // BARRA INFERIOR

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


            // SNACKBAR

            snackbarHost = {

                SnackbarHost(
                    hostState = snackbarHostState
                )
            },


            // BOTÓN FLOTANTE

            floatingActionButton = {

                FloatingActionButton(

                    onClick = {

                        scope.launch {

                            val result = snackbarHostState.showSnackbar(

                                message = "¡Hola! Este es un Snackbar",

                                actionLabel = "Aceptar",

                                duration = SnackbarDuration.Short

                            )

                            if (result == SnackbarResult.ActionPerformed) {

                                Toast.makeText(
                                    context,
                                    "Acción realizada",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
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
}


// --------------------------------------------------
// ABRIR PÁGINA WEB
// --------------------------------------------------

fun openWebScreen(context: Context) {

    val url = Uri.parse(
        "https://aguascalientes.tecnm.mx/"
    )

    val intent = Intent(
        Intent.ACTION_VIEW,
        url
    )

    try {

        context.startActivity(intent)

    } catch (e: ActivityNotFoundException) {

        Toast.makeText(
            context,
            "No se pudo abrir la página web",
            Toast.LENGTH_SHORT
        ).show()
    }
}


// --------------------------------------------------
// ABRIR WHATSAPP
// --------------------------------------------------

fun openWhatsApp(context: Context) {

    try {

        val mensaje = "Hola, este es un mensaje enviado desde mi aplicación Appmiercoles"

        val intent = Intent(Intent.ACTION_VIEW)

        val url = "https://api.whatsapp.com/send?text=" +
                Uri.encode(mensaje)

        intent.data = Uri.parse(url)

        intent.setPackage("com.whatsapp")

        context.startActivity(intent)

    } catch (e: Exception) {

        try {

            val mensaje = "Hola, este es un mensaje enviado desde mi aplicación Appmiercoles"

            val intent = Intent(Intent.ACTION_VIEW)

            val url = "https://web.whatsapp.com/send?text=" +
                    Uri.encode(mensaje)

            intent.data = Uri.parse(url)

            context.startActivity(intent)

        } catch (ex: Exception) {

            Toast.makeText(
                context,
                "No se pudo abrir WhatsApp",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}


// --------------------------------------------------
// ABRIR FACEBOOK
// --------------------------------------------------

fun openFacebook(context: Context) {

    try {

        val intent = context.packageManager
            .getLaunchIntentForPackage("com.facebook.katana")

        if (intent != null) {

            context.startActivity(intent)

        } else {

            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com")
            )

            context.startActivity(webIntent)
        }

    } catch (e: Exception) {

        Toast.makeText(
            context,
            "No se pudo abrir Facebook",
            Toast.LENGTH_SHORT
        ).show()
    }
}


// --------------------------------------------------
// MENÚ DESPLEGABLE DE LOS TRES PUNTOS
// --------------------------------------------------

@Composable
fun DropDownMenu() {

    var expanded by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Box {

        IconButton(
            onClick = {
                expanded = true
            }
        ) {

            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(
                    R.string.options_menu
                ),
                tint = Color.White
            )
        }


        DropdownMenu(

            expanded = expanded,

            onDismissRequest = {
                expanded = false
            }

        ) {


            // PERFIL

            DropdownMenuItem(

                text = {
                    Text(stringResource(R.string.profile))
                },

                onClick = {

                    expanded = false

                    Toast.makeText(
                        context,
                        "Opción Perfil",
                        Toast.LENGTH_SHORT
                    ).show()

                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Person,
                        contentDescription = null
                    )
                }
            )


            // CONFIGURACIÓN

            DropdownMenuItem(

                text = {
                    Text(stringResource(R.string.config))
                },

                onClick = {

                    expanded = false

                    Toast.makeText(
                        context,
                        "Opción Configuración",
                        Toast.LENGTH_SHORT
                    ).show()

                },

                leadingIcon = {

                    Icon(
                        Icons.Default.Settings,
                        contentDescription = null
                    )
                }
            )


            HorizontalDivider()


            // ABRIR CORREO

            DropdownMenuItem(

                text = {
                    Text("Abrir correo")
                },

                onClick = {

                    expanded = false

                    val intent = Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse("mailto:ejemplo@gmail.com")
                    )

                    try {

                        context.startActivity(intent)

                    } catch (e: ActivityNotFoundException) {

                        Toast.makeText(
                            context,
                            "No hay una aplicación de correo instalada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Correo"
                    )
                }
            )


            // ENVIAR SMS

            DropdownMenuItem(

                text = {
                    Text("Enviar SMS")
                },

                onClick = {

                    expanded = false

                    val intent = Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse("smsto:4491234567")
                    )

                    try {

                        context.startActivity(intent)

                    } catch (e: ActivityNotFoundException) {

                        Toast.makeText(
                            context,
                            "No hay una aplicación de mensajes instalada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.Message,
                        contentDescription = "SMS"
                    )
                }
            )


            // GOOGLE MAPS

            DropdownMenuItem(

                text = {
                    Text("Abrir ubicación")
                },

                onClick = {

                    expanded = false

                    val ubicacion = Uri.parse(
                        "geo:0,0?q=Aguascalientes+Mexico"
                    )

                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        ubicacion
                    )

                    try {

                        context.startActivity(intent)

                    } catch (e: ActivityNotFoundException) {

                        Toast.makeText(
                            context,
                            "No se encontró una aplicación de mapas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },

                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.AddLink,
                        contentDescription = "Ubicación"
                    )
                }
            )


            HorizontalDivider()


            // CERRAR SESIÓN

            DropdownMenuItem(

                text = {
                    Text(stringResource(R.string.logout))
                },

                onClick = {

                    expanded = false

                    Toast.makeText(
                        context,
                        "Cerrar sesión",
                        Toast.LENGTH_SHORT
                    ).show()

                },

                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null
                    )
                }
            )
        }
    }
}


// --------------------------------------------------
// CONTENIDO PRINCIPAL
// --------------------------------------------------

@Composable
fun ContentView(
    modifier: Modifier = Modifier
) {

    var counter by rememberSaveable {
        mutableIntStateOf(0)
    }

    LazyColumn(

        modifier = modifier
            .fillMaxSize()
            .background(Color.Gray)
            .padding(8.dp)

    ) {


        // IMAGEN PRINCIPAL

        item {

            Image(

                painter = painterResource(
                    id = R.drawable.papoi
                ),

                contentDescription = "Imagen principal",

                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)

            )
        }


        // BOTÓN ME GUSTA Y CONTADOR

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


        // NOMBRE DE LA MATERIA

        item {

            Text(

                text = stringResource(
                    id = R.string.materia
                ),

                fontSize = 24.sp,

                color = Color.White,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),

                textAlign = TextAlign.Center

            )
        }


        // NOMBRE

        item {

            Text(

                text = stringResource(
                    id = R.string.nombre
                ),

                fontSize = 24.sp,

                color = Color.White,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),

                textAlign = TextAlign.Center

            )
        }


        // APELLIDOS

        item {

            Text(

                text = stringResource(
                    id = R.string.apellidos
                ),

                fontSize = 24.sp,

                color = Color.White,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),

                textAlign = TextAlign.Center

            )
        }


        // GALERÍA HORIZONTAL

        item {

            LazyRow(

                horizontalArrangement = Arrangement.spacedBy(8.dp),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

            ) {

                item {

                    Image(
                        painter = painterResource(
                            id = R.drawable.papoi1
                        ),
                        contentDescription = "Imagen 1",
                        modifier = Modifier.size(120.dp)
                    )
                }

                item {

                    Image(
                        painter = painterResource(
                            id = R.drawable.papoi2
                        ),
                        contentDescription = "Imagen 2",
                        modifier = Modifier.size(120.dp)
                    )
                }

                item {

                    Image(
                        painter = painterResource(
                            id = R.drawable.papoi3
                        ),
                        contentDescription = "Imagen 3",
                        modifier = Modifier.size(120.dp)
                    )
                }

                item {

                    Image(
                        painter = painterResource(
                            id = R.drawable.papoi4
                        ),
                        contentDescription = "Imagen 4",
                        modifier = Modifier.size(120.dp)
                    )
                }

                item {

                    Image(
                        painter = painterResource(
                            id = R.drawable.papoi5
                        ),
                        contentDescription = "Imagen 5",
                        modifier = Modifier.size(120.dp)
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
