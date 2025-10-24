package com.example.uthsmarttasks.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import com.example.uthsmarttasks.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Chờ 2 giây rồi chuyển sang màn hình Onboarding
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Giao diện splash
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ảnh ở giữa
            Image(
                painter = painterResource(id = R.drawable.img1),
                contentDescription = "Splash Image",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 24.dp)
            )

            // Dòng chữ in đậm màu xanh dương
            Text(
                text = "UTH SmartTasks",
                color = Color(0xFF1565C0), // xanh dương (Blue 800)
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
