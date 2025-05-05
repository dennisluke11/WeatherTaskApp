    package com.example.weathertaskapp.ui.task.components

    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
    import com.example.weathertaskapp.data.local.TaskEntity
    import com.example.weathertaskapp.ui.task.TaskList

    @Composable
    fun TaskSection(
        title: String,
        tasks: List<TaskEntity>,
        onToggle: (TaskEntity) -> Unit,
        onDelete: (TaskEntity) -> Unit,
        onEdit: (TaskEntity) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (tasks.isEmpty()) {
                EmptyTaskCard(title = "No $title Tasks")
            } else {
                TaskList(
                    tasks = tasks,
                    onToggle = onToggle,
                    onDelete = onDelete,
                    onEdit = onEdit
                )
            }
        }
    }
