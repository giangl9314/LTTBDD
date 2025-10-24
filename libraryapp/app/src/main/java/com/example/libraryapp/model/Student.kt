package com.example.libraryapp.model

import com.example.libraryapp.model.Book

data class Student(
    val id: Int,
    val name: String,
    val borrowedBooks: MutableList<Book> = mutableListOf()
)
