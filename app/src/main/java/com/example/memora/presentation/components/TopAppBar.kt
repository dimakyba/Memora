package com.example.memora.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class TopAppBar(private val title: String) : Component {
  @Composable
  override fun Display() {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .background(MaterialTheme.colorScheme.primary),
      contentAlignment = Alignment.CenterStart
    ) {
      Text(
        text = title,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(horizontal = 16.dp),
        style = MaterialTheme.typography.labelLarge
      )
    }
  }
}