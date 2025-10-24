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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dataflownavigation.R

@Composable
fun VerifyCodeScreen(
    email: String,
    onNextClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.Start)) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Image(
            painter = painterResource(id = R.drawable.img1),
            contentDescription = "Logo",
            modifier = Modifier.height(120.dp)
        )

        Text("SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Verify Code", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("Email: $email")

        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Verification Code") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { if (code.isNotBlank()) onNextClick(code) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Next")
        }
    }
}
