package com.example.remindbyme.ui.theme

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.remindbyme.Main

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Corrected App Theme Colors
val appBackground = Color(0xFFF2F2F2)
val textColor = Color(0xFFCBCBCB)
val textGreen = Color(0xFFF2F2F2)
val buttonColor = Color(0xFF4D1717)

@Composable
fun RemindByMeTheme(activity: ComponentActivity, content: @Composable () -> Unit) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(appBackground) // Fixed the background issue
    ) { innerPadding ->
        Main(activity = activity, modifier = Modifier.padding(innerPadding))
    }
}
