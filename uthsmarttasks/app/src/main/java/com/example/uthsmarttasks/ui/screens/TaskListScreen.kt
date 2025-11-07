package com.example.uthsmarttasks.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource


data class Task(
    val id: Int,
    val title: String,
    val status: String,
    val description: String,
    val dueDate: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController) {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val list = withContext(Dispatchers.IO) {
                val url = URL("https://amock.io/api/researchUTH/tasks")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val root = JSONObject(response)
                val dataArray = root.getJSONArray("data")

                val tempList = mutableListOf<Task>()
                for (i in 0 until dataArray.length()) {
                    val obj = dataArray.getJSONObject(i)
                    tempList.add(
                        Task(
                            id = obj.getInt("id"),
                            title = obj.optString("title", "No Title"),
                            description = obj.optString("description", "No Description"),
                            status = obj.optString("status", "Unknown"),
                            dueDate = obj.optString("dueDate", "No Due Date")
                        )
                    )
                }
                tempList
            }

            tasks = list
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage = e.message ?: "Unknown error"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Logo (có thể thay bằng Image nếu bạn có logo)
                        Image(
                            painter = painterResource(id = com.example.uthsmarttasks.R.drawable.img1),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 8.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("SmartTasks", color = Color(0xFF007AFF), fontWeight = FontWeight.Bold)
                            Text(
                                "A simple and efficient to-do app",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { /* Thông báo */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Thêm task */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        when {
            isLoading -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            errorMessage != null -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: $errorMessage")
            }

            tasks.isEmpty() -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No Tasks Yet 💤")
            }

            else -> LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(tasks) { task ->
                    TaskCard(task = task) {
                        navController.navigate("taskDetail/${task.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: Task, onClick: () -> Unit) {
    val backgroundColor = when (task.status.lowercase()) {
        "in progress" -> Color(0xFFE57373) // đỏ nhạt
        "pending" -> Color(0xFFC8E6C9) // xanh nhạt
        "done" -> Color(0xFFBBDEFB) // xanh da trời nhạt
        else -> Color(0xFFF5F5F5)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Text(task.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(task.description, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Status: ${task.status}", fontWeight = FontWeight.SemiBold)
            Text("Time: ${task.dueDate}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskListScreen() {
    val sampleTasks = listOf(
        Task(1, "Complete Android Project", "In Progress", "Finish the UI, integrate API, and write documentation", "14:00 2500-03-26"),
        Task(2, "Doctor Appointment 2", "Pending", "This task is related to Work. It needs to be completed", "14:00 2500-03-26"),
        Task(3, "Meeting", "Pending", "This task is related to Fitness. It needs to be completed", "14:00 2500-03-26")
    )

    val navController = rememberNavController()

    // ✅ Scaffold nằm trong @Composable → hợp lệ
    Scaffold {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(sampleTasks) { task ->
                TaskCard(task = task, onClick = {})
            }
        }
    }
}
