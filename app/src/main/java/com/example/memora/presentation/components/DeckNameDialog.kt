package com.example.memora.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class DeckNameDialog(
  private val title: String,
  private val confirmButtonText: String,
  private val initialValue: String = "",
  private val onDismiss: () -> Unit,
  private val onConfirm: (String) -> Unit
) : Component {

  @Composable
  override fun Display() {
    var deckName by remember { mutableStateOf(initialValue) }

    AlertDialog(
      onDismissRequest = onDismiss,
      title = { Text(title) },
      text = {
        BasicTextField(
          value = deckName,
          onValueChange = { deckName = it },
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
          decorationBox = { innerTextField ->
            if (deckName.isEmpty()) {
              Text("Назва деки", color = Color.Gray)
            }
            innerTextField()
          }
        )
      },
      confirmButton = {
        Button(
          onClick = {
            if (deckName.isNotBlank()) onConfirm(deckName)
          }
        ) {
          Text(confirmButtonText)
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
