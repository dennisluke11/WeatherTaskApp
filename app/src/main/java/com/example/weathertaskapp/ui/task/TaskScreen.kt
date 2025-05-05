    package com.example.weathertaskapp.ui.task

    import androidx.compose.foundation.layout.*
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Add
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.unit.dp
    import com.example.weathertaskapp.R
    import com.example.weathertaskapp.data.local.TaskEntity
    import com.example.weathertaskapp.ui.task.components.TaskSection

    @Composable
    fun TaskScreen(viewModel: TaskViewModel) {
        val toDoTasks by viewModel.toDoTasks.collectAsState()
        val completedTasks by viewModel.completedTasks.collectAsState()

        var showDialog by remember { mutableStateOf(false) }
        var taskToEdit by remember { mutableStateOf<TaskEntity?>(null) }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    taskToEdit = null
                    showDialog = true
                }) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.task_add))
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                TaskSection(
                    title = stringResource(R.string.task_todo_card_title),
                    tasks = toDoTasks,
                    onToggle = viewModel::toggleTask,
                    onDelete = viewModel::removeTask,
                    onEdit = {
                        taskToEdit = it
                        showDialog = true
                    },
                    modifier = Modifier.weight(1f)
                )

                TaskSection(
                    title = stringResource(R.string.task_completed_card_title),
                    tasks = completedTasks,
                    onToggle = viewModel::toggleTask,
                    onDelete = viewModel::removeTask,
                    onEdit = {
                        taskToEdit = it
                        showDialog = true
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (showDialog) {
            AddTaskDialog(
                task = taskToEdit,
                onAdd = { title, description ->
                    if (taskToEdit != null) {
                        viewModel.updateTask(taskToEdit!!.copy(title = title, description = description))
                    } else {
                        viewModel.addTask(title, description)
                    }
                    showDialog = false
                    taskToEdit = null
                },
                onDismiss = {
                    showDialog = false
                    taskToEdit = null
                }
            )
        }
    }
