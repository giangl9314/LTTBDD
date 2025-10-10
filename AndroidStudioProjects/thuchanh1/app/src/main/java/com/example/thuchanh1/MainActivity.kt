package com.example.thuchanh1

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
            ThucHanh01Screen()
        }
    }
}

@Composable
fun ThucHanh01Screen() {
    var name by remember { mutableStateOf("") }
    var ageInput by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var messageColor by remember { mutableStateOf(Color.Black) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "THỰC HÀNH 01",
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Ô nhập họ tên
        TextField(
            value = name,
            onValueChange = { name = it },
            placeholder = { Text("Họ và tên") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Ô nhập tuổi
        TextField(
            value = ageInput,
            onValueChange = { ageInput = it },
            placeholder = { Text("Tuổi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nút kiểm tra
        Button(
            onClick = {
                val age = ageInput.toIntOrNull()
                if (name.isBlank() || age == null) {
                    message = "⚠️ Vui lòng nhập đầy đủ thông tin!"
                    messageColor = Color.Red
                } else {
                    messageColor = Color.Blue
                    message = when {
                        age <= 2 -> "$name là em bé 👶"
                        age in 3..6 -> "$name là trẻ em 🧒"
                        age in 7..65 -> "$name là người lớn 🧑"
                        else -> "$name là người già 👴"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text("Kiểm tra")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Kết quả
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
