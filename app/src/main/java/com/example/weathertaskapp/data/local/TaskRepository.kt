    package com.example.weathertaskapp.data.local

    class TaskRepository(private val dao: TaskDao) {

        fun getTasks() = dao.getAllTasks()

        suspend fun insert(task: TaskEntity) = dao.insertTask(task)

        suspend fun update(task: TaskEntity) = dao.updateTask(task)

        suspend fun delete(task: TaskEntity) = dao.deleteTask(task)
    }