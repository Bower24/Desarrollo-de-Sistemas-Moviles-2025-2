package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import com.example.artspace.ui.theme.ArtspaceTheme
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtspaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)

@Composable
fun ArtSpaceApp() {
    val artworks = listOf(
        Artwork(R.drawable.imagen1, "Starry Night", "Vincent van Gogh", "1889"),
        Artwork(R.drawable.imagen2, "The Persistence of Memory", "Salvador Dalí", "1931"),
        Artwork(R.drawable.imagen3, "Mona Lisa", "Leonardo da Vinci", "1503")
    )

    var index by remember { mutableStateOf(0) }

    val currentArtwork = artworks[index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Imagen de la obra
        Image(
            painter = painterResource(currentArtwork.imageRes),
            contentDescription = currentArtwork.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(1.dp, Color.Gray)
        )


        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 4.dp,
            tonalElevation = 2.dp,
            border = BorderStroke(1.dp, Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentArtwork.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    buildAnnotatedString {
                        append(currentArtwork.artist)
                        withStyle(style = SpanStyle(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)) {
                            append(" (${currentArtwork.year})")
                        }
                    },
                    fontSize = 16.sp
                )
            }
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (index > 0) index-- else index = artworks.lastIndex
                }
            ) {
                Text("Previous")
            }

            Button(
                onClick = {
                    if (index < artworks.lastIndex) index++ else index = 0
                }
            ) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtSpacePreview() {
    ArtspaceTheme {
        ArtSpaceApp()
    }
}
