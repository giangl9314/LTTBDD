package com.example.libraryapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.libraryapp.ui.screens.BookListScreen
import com.example.libraryapp.ui.screens.ManagementScreen
import com.example.libraryapp.ui.screens.StudentListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryApp() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Quản lý") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("DS Sách") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Sinh viên") }
                )
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> ManagementScreen(Modifier.padding(innerPadding))
            1 -> BookListScreen(Modifier.padding(innerPadding))
            2 -> StudentListScreen(Modifier.padding(innerPadding))
        }
    }
}
