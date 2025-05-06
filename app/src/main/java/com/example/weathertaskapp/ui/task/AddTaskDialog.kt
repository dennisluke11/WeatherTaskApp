package com.example.weathertaskapp.ui.task

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import com.example.weathertaskapp.R
import com.example.weathertaskapp.data.local.TaskEntity
import com.example.weathertaskapp.utils.Dimens

@Composable
fun AddTaskDialog(
    task: TaskEntity?,
    onAdd: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (task == null) context.getString(R.string.new_task_title) else context.getString(
                    R.string.edit_task_title
                )
            )
        },
        text = {
            Column(modifier = Modifier.padding(Dimens.PaddingSmall)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(context.getString(R.string.task_title_label)) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(Dimens.SpacerHeightSmall))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(context.getString(R.string.task_description_label)) },
                    maxLines = 2,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    onAdd(title.trim(), description.trim())
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.empty_fields_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Text(
                    if (task == null) context.getString(R.string.add_button) else context.getString(
                        R.string.update_button
                    )
                )
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(text = context.getString(R.string.cancel_button))
            }
        }
    )
}
