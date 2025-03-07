package com.example.memora.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color

@Composable
fun CreateDeckDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
  var deckName by remember { mutableStateOf("") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Введите название деки") },
    text = {
      BasicTextField(
        value = deckName,
        onValueChange = { deckName = it },
        modifier = Modifier.fillMaxWidth().background(Color.LightGray)

      )
    },
    confirmButton = {
      Button(
        onClick = {
          if (deckName.isNotBlank()) onConfirm(deckName)
        }
      ) {
        Text("Создать")
      }
    },
    dismissButton = {
      Button(onClick = onDismiss) {
        Text("Отмена")
      }
    }
  )
}
