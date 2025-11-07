    // RetrofitClient.kt
    package com.example.uthsmarttasks.network

    import retrofit2.Retrofit
    import retrofit2.converter.gson.GsonConverterFactory
    import retrofit2.http.*

    object RetrofitClient {
        val instance: TaskApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://amock.io/api/researchUTH/") // nhớ dấu /
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TaskApi::class.java)
        }
    }

    interface TaskApi {
        @GET("task/{id}")
        fun getTask(@Path("id") id: Int): retrofit2.Call<TaskResponse>

        @DELETE("task/{id}")
        fun deleteTask(@Path("id") id: Int): retrofit2.Call<ApiDeleteResponse>
    }

    // Data classes
    data class TaskResponse(
        val isSuccess: Boolean,
        val message: String,
        val data: Task
    )

    data class Task(
        val id: Int,
        val title: String,
        val description: String,
        val status: String,
        val priority: String?,
        val category: String?,
        val dueDate: String?,
        val subtasks: List<Subtask>?,
        val attachments: List<Attachment>?,
        val reminders: List<Reminder>?
    )

    data class Subtask(val id: Int, val title: String, val isCompleted: Boolean)
    data class Attachment(val id: Int, val fileName: String, val fileUrl: String)
    data class Reminder(val id: Int, val type: String, val time: String)
    data class ApiDeleteResponse(val isSuccess: Boolean, val message: String)
