package com.example.memora.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memora.core.data.DeckEntity

class DeckItem(
  private val deck: DeckEntity,
  private val cardCount: Int,
  private val onClick: () -> Unit,
  private val onRenameClick: () -> Unit,
  private val onDeleteClick: () -> Unit,
  private val onExportClick: () -> Unit
) : Component {

  @Composable
  override fun Display() {
    var expanded by remember { mutableStateOf(false) }

    Card(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(0.8f)
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
        // Preview container
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color.LightGray)
        )

        // Title and counter
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
          contentAlignment = Alignment.BottomCenter
        ) {
          Column(
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.Top,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = deck.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
              )

              IconButton(
                onClick = { expanded = true },
                modifier = Modifier.size(24.dp)
              ) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
              }

              DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
              ) {
                DropdownMenuItem(
                  text = { Text("Перейменувати") },
                  onClick = {
                    expanded = false
                    onRenameClick()
                  }
                )
                DropdownMenuItem(
                  text = { Text("Видалити") },
                  onClick = {
                    expanded = false
                    onDeleteClick()
                  }
                )
                DropdownMenuItem(
                  text = { Text("Експорт") },
                  onClick = {
                    expanded = false
                    // onExportClick()
                  }
                )
              }
            }

            Text(
              text = "($cardCount)",
              fontSize = 12.sp,
              color = Color.Gray
            )
          }
        }
      }
    }
  }
}
