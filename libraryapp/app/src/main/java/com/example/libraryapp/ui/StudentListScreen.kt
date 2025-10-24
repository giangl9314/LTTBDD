package com.example.libraryapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StudentListScreen(modifier: Modifier = Modifier) {
    val students = mapOf(
        "Nguyen Van A" to 2,
        "Nguyen Thi B" to 1,
        "Nguyen Van C" to 0
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Danh sách Sinh viên",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(16.dp))

        students.forEach { (name, borrowedCount) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(name, fontWeight = FontWeight.Bold)
                    Text("Số sách đã mượn: $borrowedCount")
                }
            }
        }
    }
}
