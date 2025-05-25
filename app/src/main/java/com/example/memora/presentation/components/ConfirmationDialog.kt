package com.example.memora.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class ConfirmationDialog(
  private val title: String,
  private val message: String,
  private val confirmButtonText: String = "Підтвердити",
  private val dismissButtonText: String = "Відміна",
  private val onConfirm: () -> Unit,
  private val onDismiss: () -> Unit
) : Component {

  @Composable
  override fun Display() {
    AlertDialog(
      onDismissRequest = onDismiss,
      title = { Text(title) },
      text = { Text(message) },
      confirmButton = {
        Button(
          onClick = onConfirm
        ) {
          Text(confirmButtonText)
        }
      },
      dismissButton = {
        Button(onClick = onDismiss) {
          Text(dismissButtonText)
        }
      }
    )
  }
}
