package com.example.uthsmarttasks.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uthsmarttasks.R
import kotlinx.coroutines.launch

data class OnboardingPage(
    val image: Int,
    val title: String,
    val description: String
)

@Composable
fun OnboardingScreen(navController: NavController) {
    val pages = listOf(
        OnboardingPage(
            R.drawable.img2,
            "Easy Task Management",
            "Organize your daily tasks easily and effectively."
        ),
        OnboardingPage(
            R.drawable.img3,
            "Increase Work Efficiency",
            "Focus on priorities and improve your productivity."
        ),
        OnboardingPage(
            R.drawable.img4,
            "Receive Notifications",
            "Stay updated with smart reminders and alerts."
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // === Thanh chỉ trang và Skip ===
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dấu chấm chỉ trang
            Row {
                repeat(pages.size) { index ->
                    val color = if (pagerState.currentPage == index)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(10.dp)
                            .background(color = color, shape = CircleShape)
                    )
                }
            }

            // Nút Skip
            TextButton(onClick = {
                navController.navigate("home") {
                    popUpTo("onboarding") { inclusive = true }
                }
            }) {
                Text(
                    text = "Skip",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // === Nội dung chính ===
        HorizontalPager(state = pagerState) { page ->
            val item = pages[page]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = item.image),
                    contentDescription = item.title,
                    modifier = Modifier
                        .size(280.dp)
                        .padding(bottom = 24.dp)
                )
                Text(
                    text = item.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.description,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    lineHeight = 22.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nút mũi tên bo tròn
            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage > 0) {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }
                },
                enabled = pagerState.currentPage > 0,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pagerState.currentPage > 0)
                        MaterialTheme.colorScheme.primary
                    else
                        Color.Gray.copy(alpha = 0.3f),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Previous"
                )
            }

            // Nút Next / Get Started
            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < pages.lastIndex) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            navController.navigate("login") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Next",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
        }
    }
}
