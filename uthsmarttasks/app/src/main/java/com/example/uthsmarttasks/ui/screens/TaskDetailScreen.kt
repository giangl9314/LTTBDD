package com.example.uthsmarttasks.ui.screens

import com.example.uthsmarttasks.network.Task
import com.example.uthsmarttasks.network.Subtask
import com.example.uthsmarttasks.network.Attachment
import com.example.uthsmarttasks.network.Reminder


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uthsmarttasks.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*


import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(navController: NavController, taskId: String?) {
    var task by remember { mutableStateOf<Task?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(taskId) {
        // Chuyển String? sang Int? an toàn
        val id = taskId?.toIntOrNull()
        if (id == null) {
            isLoading = false
            return@LaunchedEffect
        }

        RetrofitClient.instance.getTask(id).enqueue(object : Callback<TaskResponse> {
            override fun onResponse(call: Call<TaskResponse>, response: Response<TaskResponse>) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    task = response.body()?.data
                }
                isLoading = false
            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                t.printStackTrace()
                isLoading = false
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail", color = Color(0xFF007AFF), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Delete Task
                        val id = taskId?.toIntOrNull() ?: return@IconButton
                        RetrofitClient.instance.deleteTask(id).enqueue(object : Callback<ApiDeleteResponse> {
                            override fun onResponse(call: Call<ApiDeleteResponse>, response: Response<ApiDeleteResponse>) {
                                if (response.body()?.isSuccess == true) navController.popBackStack()
                            }

                            override fun onFailure(call: Call<ApiDeleteResponse>, t: Throwable) {
                                t.printStackTrace()
                            }
                        })
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFFF5722))
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            when {
                isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                task == null -> Text("Task not found ❌", Modifier.align(Alignment.Center))
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Title + Description
                        Text(task!!.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(task!!.description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                        Spacer(Modifier.height(12.dp))

                        // Category / Status / Priority section
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFFCE4EC), shape = RoundedCornerShape(12.dp))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            InfoChip("Category", task!!.category ?: "N/A")
                            InfoChip("Status", task!!.status)
                            InfoChip("Priority", task!!.priority ?: "Normal")
                        }

                        Spacer(Modifier.height(20.dp))
                        Text("Subtasks", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        Spacer(Modifier.height(8.dp))
                        if (task!!.subtasks.isNullOrEmpty()) {
                            Text("No subtasks", color = Color.Gray)
                        } else {
                            task!!.subtasks!!.forEach { subtask ->
                                SubtaskItem(subtask)
                                Spacer(Modifier.height(8.dp))
                            }
                        }

                        Spacer(Modifier.height(20.dp))
                        Text("Attachments", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))

                        if (task!!.attachments.isNullOrEmpty()) {
                            Text("No attachments", color = Color.Gray)
                        } else {
                            task!!.attachments!!.forEach { attachment ->
                                AttachmentItem(attachment)
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SubtaskItem(subtask: Subtask) {
    var checked by remember { mutableStateOf(subtask.isCompleted) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF007AFF))
        )
        Spacer(Modifier.width(8.dp))
        Text(subtask.title, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun AttachmentItem(attachment: Attachment) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Link, contentDescription = "Attachment", tint = Color.Gray)
        Spacer(Modifier.width(8.dp))
        Text(attachment.fileName, style = MaterialTheme.typography.bodyMedium)
    }
}
