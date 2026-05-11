package com.example.shernandezmusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shernandezmusicapp.ui.navigation.Detail
import com.example.shernandezmusicapp.ui.navigation.Home
import com.example.shernandezmusicapp.ui.screens.DetailScreen
import com.example.shernandezmusicapp.ui.screens.HomeScreen
import com.example.shernandezmusicapp.ui.theme.SHernandezMusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SHernandezMusicAppTheme {
                MusicApp()
            }
        }
    }
}

@Composable
fun MusicApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(onAlbumClick = { id ->
                navController.navigate(Detail(albumId = id))
            })
        }
        composable<Detail> { backStackEntry ->
            val detail: Detail = backStackEntry.toRoute()
            DetailScreen(
                albumId = detail.albumId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
