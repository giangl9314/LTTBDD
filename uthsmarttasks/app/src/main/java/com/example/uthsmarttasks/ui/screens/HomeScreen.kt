package com.example.uthsmarttasks.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class SimpleTask(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val date: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val taskList = remember {
        mutableStateListOf(
            SimpleTask(1,"Complete Android Project","Finish UI & integrate API","In Progress","14:00 22/03/26", Color(0xFFFFCDD2)),
            SimpleTask(2,"Doctor Appointment 2","This task is related to Work","Pending","14:00 22/03/26", Color(0xFFC8E6C9)),
            SimpleTask(3,"Meeting","This task is related to Fitness","Pending","14:00 22/03/26", Color(0xFFBBDEFB))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("SmartTasks", color = Color(0xFF1E88E5), fontWeight = androidx.compose.ui.text.font.FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White))
        },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = {}, containerColor = Color(0xFF1E88E5)) {
                Icon(Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(8.dp)
        ) {
            items(taskList) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("task_detail/${task.id}") },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = task.color)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(task.title, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontSize = 18.sp)
                        Text(task.description, fontSize = 14.sp)
                        Text("Status: ${task.status}", fontWeight = androidx.compose.ui.text.font.FontWeight.Medium)
                        Text(task.date, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(selected = true, onClick = { navController.navigate("home") },
            label = { Text("Home") }, icon = { Icon(Icons.Default.Home, contentDescription = "Home") })
        NavigationBarItem(selected = false, onClick = { navController.navigate("profile") },
            label = { Text("Profile") }, icon = { Icon(Icons.Default.Person, contentDescription = "Profile") })
    }
}
