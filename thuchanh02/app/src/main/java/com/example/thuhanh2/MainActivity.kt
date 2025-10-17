package com.example.thuhanh2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThucHanh02Screen()
        }
    }
}

@Composable
fun ThucHanh02Screen() {
    // 🟢 Biến trạng thái
    var input by remember { mutableStateOf("") }
    var numbers by remember { mutableStateOf(listOf<Int>()) }
    var error by remember { mutableStateOf("") }

    // 🟢 Giao diện chính
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "Thực hành 02",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hàng chứa TextField + Nút
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("Nhập vào số lượng") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                singleLine= true,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                val n = input.toIntOrNull()
                if (n == null || n <= 0) {
                    error = "⚠️ Dữ liệu bạn nhập không hợp lệ!"
                    numbers = emptyList()
                } else {
                    error = ""
                    numbers = (1..n).toList()
                }
            }) {
                Text("Tạo")
            }
        }

        // Thông báo lỗi (nếu có)
        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = Color.Red,
                fontSize = 14.sp
            )
        }

        // Danh sách các nút số
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            items(numbers) { num ->
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = num.toString(), color = Color.White)
                }
            }
        }
    }
}
