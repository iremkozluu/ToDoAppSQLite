package com.ikozlu.todoappsqlite.data.model

data class Note(
    val title: String,
    val description: String,
    val priority: String,
    val id: Int = 0
    )
