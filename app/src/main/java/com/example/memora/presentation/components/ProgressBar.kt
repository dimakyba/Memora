package com.example.memora.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.unit.dp

class ProgressBar(private val progress: Int) : Component {
  @Composable
  override fun Display() {
    val progressWidth = 0.6f // 60% ширини контейнера

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Контейнер для заповнюваної смужки
      Box(
        modifier = Modifier
          .weight(progressWidth)
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
          .semantics {
            progressBarRangeInfo = androidx.compose.ui.semantics.ProgressBarRangeInfo(
              current = progress.toFloat(),
              range = 0f..100f,
              steps = 0
            )
          }
      ) {
        // Заповнювана частина
        Box(
          modifier = Modifier
            .fillMaxWidth(progress / 100f)
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(getProgressColor())
        )
      }

      // Пустий простір (20%)
      Spacer(modifier = Modifier.weight(0.2f))

      // Текст з відсотками
      Text(
        text = "$progress%",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(start = 8.dp)
      )
    }
  }

  private fun getProgressColor(): Color {
    return when {
      progress < 33 -> Color(0xFFE57373) // Light Red
      progress < 66 -> Color(0xFFFFB74D) // Light Orange
      else -> Color(0xFF81C784) // Light Green
    }
  }
}
