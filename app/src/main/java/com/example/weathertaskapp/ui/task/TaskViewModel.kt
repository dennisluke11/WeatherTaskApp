package com.example.weathertaskapp.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathertaskapp.data.local.TaskEntity
import com.example.weathertaskapp.data.local.TaskRepository
import com.example.weathertaskapp.utils.FlowConstants.SHARING_TIMEOUT_MS
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = repository.getTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(SHARING_TIMEOUT_MS), emptyList())

    val toDoTasks: StateFlow<List<TaskEntity>> = _tasks
        .map { tasks -> tasks.filter { !it.isCompleted } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(SHARING_TIMEOUT_MS), emptyList())

    val completedTasks: StateFlow<List<TaskEntity>> = _tasks
        .map { tasks -> tasks.filter { it.isCompleted } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(SHARING_TIMEOUT_MS), emptyList())

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            repository.insert(TaskEntity(title = title, description = description))
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.update(task)
        }
    }

    fun toggleTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.update(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun removeTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.delete(task)
        }
    }
}
