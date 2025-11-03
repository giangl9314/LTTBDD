package com.example.uthsmarttasks.ui.screens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun TaskListScreen(navController: NavController) {
    var tasks by remember { mutableStateOf(listOf<Map<String, String>>()) }
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val url = URL("https://amock.io/api/researchUTH/tasks")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val response = connection.inputStream.bufferedReader().use { it.readText() }
                println(">>> RESPONSE: $response")

                val root = JSONObject(response)
                val dataArray = root.getJSONArray("data")

                val list = mutableListOf<Map<String, String>>()
                for (i in 0 until dataArray.length()) {
                    val obj = dataArray.getJSONObject(i)
                    list.add(
                        mapOf(
                            "id" to obj.getInt("id").toString(),
                            "title" to obj.getString("title"),
                            "status" to obj.getString("status")
                        )
                    )
                }

                tasks = list
                println(">>> TASKS LOADED: ${tasks.size}")
            } catch (e: Exception) {
                e.printStackTrace()
                println(">>> ERROR: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    when {
        isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

        tasks.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No Tasks Yet 💤")
        }

        else -> LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("taskDetail/${task["id"]}")
                        }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(task["title"] ?: "", style = MaterialTheme.typography.titleMedium)
                        Text("Status: ${task["status"]}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
