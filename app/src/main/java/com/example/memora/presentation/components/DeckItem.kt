package com.example.memora.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memora.core.data.DeckEntity

class DeckItem(
  private val deck: DeckEntity,
  private val cardCount: Int,
  private val onClick: () -> Unit
) : Component {

  @Composable
  override fun Display() {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .clickable { onClick() },
      shape = RoundedCornerShape(8.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color.LightGray)
        )

        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.Start
        ) {
          Text(
            text = deck.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            modifier = Modifier.padding(bottom = 4.dp)
          )

          Text(
            text = "Карток: $cardCount",
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 1
          )
        }
      }
    }
  }
}
