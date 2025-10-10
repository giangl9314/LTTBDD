package com.example.thuchanh2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmailCheckScreen()
        }
    }
}

@Composable
fun EmailCheckScreen() {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var messageColor by remember { mutableStateOf(Color.Red) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "Thực hành 02",
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ô nhập email
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Nút kiểm tra
        Button(
            onClick = {
                when {
                    email.isBlank() -> {
                        message = "Email không hợp lệ"
                        messageColor = Color.Red
                    }
                    !email.contains("@") -> {
                        message = "Email không đúng định dạng"
                        messageColor = Color.Red
                    }
                    else -> {
                        message = "Bạn đã nhập email hợp lệ"
                        messageColor = Color.Green
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text("Kiểm tra")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Thông báo kết quả
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = messageColor,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
