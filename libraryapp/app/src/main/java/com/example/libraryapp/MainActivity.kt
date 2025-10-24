package com.example.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.libraryapp.ui.LibraryApp
import com.example.libraryapp.ui.theme.LibraryappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryappTheme {
                LibraryApp()
            }
        }
    }
}
