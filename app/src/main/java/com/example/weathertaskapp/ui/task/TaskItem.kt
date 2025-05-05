        package com.example.weathertaskapp.ui.task

        import androidx.compose.foundation.layout.*
        import androidx.compose.material.icons.Icons
        import androidx.compose.material.icons.filled.Edit
        import androidx.compose.material3.*
        import androidx.compose.runtime.Composable
        import androidx.compose.ui.Alignment
        import androidx.compose.ui.Modifier
        import androidx.compose.ui.res.stringResource
        import androidx.compose.ui.unit.dp
        import com.example.weathertaskapp.R
        import com.example.weathertaskapp.data.local.TaskEntity

        @Composable
        fun TaskItem(
            task: TaskEntity,
            onToggle: () -> Unit,
            onDelete: () -> Unit,
            onEdit: () -> Unit
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.task_title_formatted, task.title),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(R.string.task_description_formatted, task.description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (task.isCompleted) stringResource(R.string.task_completed) else stringResource(R.string.task_pending) ,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (task.isCompleted) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = onToggle) {
                            Text(if (task.isCompleted) stringResource(R.string.undo_button) else stringResource(R.string.complete_button))
                        }

                        IconButton(onClick = onEdit) {
                            Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit_task_content_desc))
                        }

                        OutlinedButton(onClick = onDelete) {
                            Text(stringResource(R.string.delete_button))
                        }
                    }
                }
            }
        }
