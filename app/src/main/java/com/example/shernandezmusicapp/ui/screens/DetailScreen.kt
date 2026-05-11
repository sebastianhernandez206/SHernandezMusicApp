package com.example.shernandezmusicapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.shernandezmusicapp.data.model.Album
import com.example.shernandezmusicapp.data.network.MusicApiService
import com.example.shernandezmusicapp.ui.components.MiniPlayer
import com.example.shernandezmusicapp.ui.components.TrackItem

@Composable
fun DetailScreen(albumId: String, onBackClick: () -> Unit) {
    var album by remember { mutableStateOf<Album?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(albumId) {
        try {
            album = MusicApiService.create().getAlbum(albumId)
            isLoading = false
        } catch (e: Exception) {
            errorMessage = e.message
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF3E5F5))) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: $errorMessage")
            }
        } else album?.let { currentAlbum ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                item {
                    AlbumHeader(currentAlbum, onBackClick)
                }

                item {
                    AboutAlbumSection(currentAlbum.description)
                }

                item {
                    ArtistChip(currentAlbum.artist)
                }

                items(10) { index ->
                    TrackItem(
                        albumTitle = currentAlbum.title,
                        artist = currentAlbum.artist,
                        imageUrl = currentAlbum.image,
                        trackNumber = index + 1
                    )
                }
            }

            MiniPlayer(
                album = currentAlbum,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun AlbumHeader(album: Album, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
    ) {
        AsyncImage(
            model = album.image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xFF4A148C).copy(alpha = 0.8f)),
                        startY = 400f
                    )
                )
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), CircleShape)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.background(Color.Black.copy(alpha = 0.3f), CircleShape)) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Text(
                text = album.title,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = album.artist,
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)),
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(
                    onClick = { /* TODO */ },
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(brush = Brush.linearGradient(listOf(Color.White, Color.White))),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(Icons.Default.Shuffle, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun AboutAlbumSection(description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "About this album",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF4A148C)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ArtistChip(artist: String) {
    Surface(
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = null
    ) {
        Text(
            text = "Artist: $artist",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color(0xFF4A148C),
            fontWeight = FontWeight.Medium
        )
    }
}
