    package com.example.weathertaskapp.ui.task

    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.unit.dp
    import com.example.weathertaskapp.data.local.TaskEntity

    @Composable
    fun TaskList(
        tasks: List<TaskEntity>,
        onToggle: (TaskEntity) -> Unit,
        onDelete: (TaskEntity) -> Unit,
        onEdit: (TaskEntity) -> Unit
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskItem(
                    task = task,
                    onToggle = { onToggle(task) },
                    onDelete = { onDelete(task) },
                    onEdit = { onEdit(task) }
                )
            }
        }
    }
