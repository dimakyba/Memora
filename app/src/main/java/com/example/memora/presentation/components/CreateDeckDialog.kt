package com.example.memora.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


class CreateDeckDialog(
  private val onDismiss: () -> Unit,
  private val onConfirm: (String) -> Unit
) : Component {

  @Composable
  override fun Display() {
    var deckName by remember { mutableStateOf("") }

    AlertDialog(
      onDismissRequest = onDismiss,
      title = { Text("Введіть назву деки:") },
      text = {
        BasicTextField(
          value = deckName,
          onValueChange = { deckName = it },
          modifier = Modifier.fillMaxWidth()

        )
      },
      confirmButton = {
        Button(
          onClick = {
            if (deckName.isNotBlank()) onConfirm(deckName)
          }
        ) {
          Text("Створити")
        }
      },
      dismissButton = {
        Button(onClick = onDismiss) {
          Text("Відміна")
        }
      }
    )
  }
}