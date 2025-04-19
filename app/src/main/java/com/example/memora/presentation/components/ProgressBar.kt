package com.example.memora.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class ProgressBar(private val progress: Int) : Component {
  @Composable
  override fun Display() {
    val progressWidth = 0.5f // 50% ширины контейнера

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Контейнер для заполняющейся полоски
      Box(
        modifier = Modifier
          .weight(progressWidth)
          .height(8.dp)
          .clip(RoundedCornerShape(4.dp))
          .background(Color.LightGray) // Фон прогрессбара
      ) {
        // Заполняющаяся часть
        Box(
          modifier = Modifier
            .fillMaxWidth(progress / 100f) // Заполнение в зависимости от прогресса
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(getProgressColor())
        )
      }

      // Пустое пространство (25%)
      Spacer(modifier = Modifier.weight(0.25f))

      // Текст с процентами
      Text(
        text = "$progress%",
        modifier = Modifier.padding(start = 8.dp)
      )
    }
  }


  private fun getProgressColor(): Color {
    return when {
      progress < 33 -> Color.Red
      progress < 66 -> Color(0xFFFFA500) // Оранжевый
      else -> Color.Green
    }
  }

}