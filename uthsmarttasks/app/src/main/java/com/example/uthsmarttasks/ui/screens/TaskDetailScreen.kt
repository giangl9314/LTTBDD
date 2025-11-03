package com.example.uthsmarttasks.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun TaskDetailScreen(navController: NavController, taskId: String?) {
    var task by remember { mutableStateOf<Map<String, String>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(taskId) {
        scope.launch {
            try {
                val url = URL("https://amock.io/api/researchUTH/task/$taskId")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                val data = conn.inputStream.bufferedReader().readText()
                val root = JSONObject(data)
                val json = root.getJSONObject("data")

                task = mapOf(
                    "title" to json.getString("title"),
                    "description" to json.getString("description"),
                    "status" to json.getString("status")
                )
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    when {
        isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        task != null -> Column(Modifier.padding(24.dp)) {
            Text(task!!["title"] ?: "", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text(task!!["description"] ?: "")
            Spacer(Modifier.height(8.dp))
            Text("Status: ${task!!["status"]}")
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        val url = URL("https://amock.io/api/researchUTH/task/$taskId")
                        val conn = url.openConnection() as HttpURLConnection
                        conn.requestMethod = "DELETE"
                        conn.inputStream.close()
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Delete Task", color = Color.White)
            }
        }

        else -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Task not found ❌")
        }
    }
}
