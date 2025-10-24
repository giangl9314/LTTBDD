package com.example.libraryapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ManagementScreen(modifier: Modifier = Modifier) {
    var currentStudent by remember { mutableStateOf("Nguyen Van A") }

    val borrowedBooks = remember(currentStudent) {
        when (currentStudent) {
            "Nguyen Van A" -> listOf("Sách 01", "Sách 02")
            "Nguyen Thi B" -> listOf("Sách 01")
            else -> emptyList()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Hệ thống Quản lý Thư viện",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = currentStudent,
                onValueChange = { currentStudent = it },
                label = { Text("Sinh viên") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = { /* xử lý đổi sinh viên */ }) {
                Text("Thay đổi")
            }
        }

        Spacer(Modifier.height(16.dp))
        Text("Danh sách sách", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        if (borrowedBooks.isEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Bạn chưa mượn quyển sách nào", fontWeight = FontWeight.SemiBold)
                    Text("Nhấn ‘Thêm’ để bắt đầu hành trình đọc sách!")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                items(borrowedBooks.size) { index ->
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            Modifier
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(borrowedBooks[index])
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = { /* thêm sách */ }) {
            Text("Thêm")
        }
    }
}
