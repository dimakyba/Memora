package com.example.memora.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class AddButton(
  private val onClick: () -> Unit,
  private val contentDescription: String
) : Component {

  @Composable
  override fun Display() {
    FloatingActionButton(
      onClick = onClick,
      modifier = Modifier.padding(16.dp),
      shape = MaterialTheme.shapes.medium
    ) {
      Icon(Icons.Default.Add, contentDescription = contentDescription)
    }
  }
}
