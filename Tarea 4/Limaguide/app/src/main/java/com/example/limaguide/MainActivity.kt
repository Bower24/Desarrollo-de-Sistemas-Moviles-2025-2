package com.example.limaguide

// Importación para calcular la clase de tamaño de ventana (requiere androidx.compose.material3:material3-window-size-class)
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.limaguide.UI1.LimaApp
import com.example.limaguide.UI1.theme.LimaguideTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LimaguideTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Calcula la clase de tamaño de la ventana para diseños adaptables
                    val windowSize = calculateWindowSizeClass(this)
                    LimaApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}

