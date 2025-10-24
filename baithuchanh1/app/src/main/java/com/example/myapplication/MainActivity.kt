package com.example.baithuchanh1
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
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
            MaterialTheme {
                ThucHanh02Screen()
            }
        }
    }
}

@Composable
fun ThucHanh02Screen() {
    // 🟢 Biến trạng thái
    var name by remember { mutableStateOf("") }
    var ageInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    // 🟢 Giao diện chính
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề
        Text(
            text = "THỰC HÀNH 01",
            fontSize = 24.sp,
            color = Color(0xFF1E88E5)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Ô nhập họ tên
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Họ và tên") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Ô nhập tuổi
        TextField(
            value = ageInput,
            onValueChange = { ageInput = it },
            label = { Text("Tuổi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nút kiểm tra
        Button(
            onClick = {
                val age = ageInput.toIntOrNull()
                if (name.isBlank() || age == null || age < 0) {
                    error = "⚠️ Vui lòng nhập đúng tên và tuổi!"
                    result = ""
                } else {
                    error = ""
                    result = when {
                        age > 65 -> "$name là người già 👵"
                        age in 6..65 -> "$name là người lớn 👨"
                        age in 2..6 -> "$name là trẻ em 👧"
                        age <= 2 -> "$name là em bé 👶"
                        else -> ""
                    }
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Kiểm tra", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị lỗi
        if (error.isNotEmpty()) {
            Text(text = error, color = Color.Red, fontSize = 14.sp)
        }

        // Kết quả
        if (result.isNotEmpty()) {
            Text(
                text = result,
                color = Color(0xFF1E88E5),
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
