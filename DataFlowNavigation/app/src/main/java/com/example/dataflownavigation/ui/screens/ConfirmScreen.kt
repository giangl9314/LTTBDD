package com.example.dataflownavigation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.dataflownavigation.R

@Composable
fun ConfirmScreen(
    email: String,
    verifyCode: String,
    password: String,
    onBackClick: () -> Unit
) {
    var inputEmail by remember { mutableStateOf(email) }
    var inputVerifyCode by remember { mutableStateOf(verifyCode) }
    var inputPassword by remember { mutableStateOf(password) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 🔙 Nút quay lại
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.Start)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // 🖼️ Logo
        Image(
            painter = painterResource(id = R.drawable.img1),
            contentDescription = "Logo",
            modifier = Modifier
                .height(120.dp)
                .padding(bottom = 16.dp)
        )

        // 🏷️ Tiêu đề
        Text("SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Confirm", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("We are here to help you!")

        Spacer(modifier = Modifier.height(24.dp))

        // ✉️ Trường email
        OutlinedTextField(
            value = inputEmail,
            onValueChange = { inputEmail = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔢 Trường Verify Code (mới thêm)
        OutlinedTextField(
            value = inputVerifyCode,
            onValueChange = { inputVerifyCode = it },
            label = { Text("Verify Code") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔑 Trường Password
        OutlinedTextField(
            value = inputPassword,
            onValueChange = { inputPassword = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Nút Submit
        Button(
            onClick = {
                // xử lý xác nhận (ví dụ gửi email + verifyCode + password lên server)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
