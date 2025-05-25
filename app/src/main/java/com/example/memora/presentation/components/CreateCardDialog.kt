package com.example.memora.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.memora.core.algorithms.AlgorithmType

class CreateCardDialog(
  private val onDismiss: () -> Unit,
  private val onConfirm: (String, String, AlgorithmType) -> Unit
) : Component {

  @Composable
  override fun Display() {
    var name by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedAlgorithm by remember { mutableStateOf(AlgorithmType.FIXED_INTERVAL) }
    val scrollState = rememberScrollState()

    AlertDialog(
      onDismissRequest = onDismiss,
      title = { Text("Створення картки") },
      text = {
        Column {
          BasicTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 8.dp),
            decorationBox = { innerTextField ->
              if (name.isEmpty()) {
                Text("Назва картки", color = Color.Gray)
              }
              innerTextField()
            }
          )

          BasicTextField(
            value = content,
            onValueChange = { content = it },
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp)
              .verticalScroll(scrollState)
              .padding(bottom = 8.dp),
            decorationBox = { innerTextField ->
              if (content.isEmpty()) {
                Text("Контент картки", color = Color.Gray)
              }
              innerTextField()
            }
          )

          Text("Тип алгоритму:", modifier = Modifier.padding(bottom = 4.dp))
          AlgorithmType.entries.forEach { algorithm ->
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedAlgorithm = algorithm }
                .padding(vertical = 4.dp)
            ) {
              RadioButton(
                selected = algorithm == selectedAlgorithm,
                onClick = { selectedAlgorithm = algorithm }
              )
              Text(
                text = when(algorithm) {
                  AlgorithmType.FIXED_INTERVAL -> "Фіксований інтервал"
                  AlgorithmType.ADAPTIVE -> "Адаптивний"
                },
                modifier = Modifier.padding(start = 8.dp)
              )
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (name.isNotBlank() && content.isNotBlank()) {
              onConfirm(name, content, selectedAlgorithm)
            }
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
