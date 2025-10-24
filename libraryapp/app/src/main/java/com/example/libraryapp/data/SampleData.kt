package com.example.libraryapp.data

import com.example.libraryapp.model.*

val bookList = mutableListOf(
    Book(1, "Lập trình Android cơ bản"),
    Book(2, "Nhập môn Trí tuệ nhân tạo"),
    Book(3, "Phân tích và thiết kế hệ thống")
)

val studentList = mutableListOf(
    Student(1, "Nguyễn Văn A", mutableListOf(bookList[0])),
    Student(2, "Trần Thị B", mutableListOf(bookList[1])),
    Student(3, "Lê Văn C")
)
