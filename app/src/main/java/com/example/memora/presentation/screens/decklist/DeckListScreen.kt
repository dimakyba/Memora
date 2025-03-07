
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memora.data.DeckEntity
import com.example.memora.presentation.components.CreateDeckDialog

@Composable
fun DeckListScreen(viewModel: DeckViewModel) {
  var showDialog by remember { mutableStateOf(false) }

  // Получаем список дек из ViewModel
  val decks by viewModel.decks.collectAsStateWithLifecycle(
    initialValue = emptyList(),
    lifecycleOwner = LocalLifecycleOwner.current
  )

  Box(modifier = Modifier.fillMaxSize()) {
    // Сетка дек
    LazyVerticalGrid(
      columns = GridCells.Fixed(3), // Три колонки
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(decks) { deck ->
        // Получаем количество карточек для текущей деки
        val cardCount by produceState(initialValue = 0, key1 = deck.id) {
          value = viewModel.getCardCountForDeck(deck.id)
        }
        DeckItem(deck = deck, cardCount = cardCount)
      }
    }

    // Кнопка создания деки в правом нижнем углу
    FloatingActionButton(
      onClick = { showDialog = true },
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(16.dp),
      shape = CircleShape,
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
      Icon(Icons.Default.Add, contentDescription = "Создать деку")
    }
  }

  // Диалог создания деки
  if (showDialog) {
    CreateDeckDialog(
      onDismiss = { showDialog = false },
      onConfirm = { name ->
        viewModel.addDeck(name)
        showDialog = false
      }
    )
  }
}

@Composable
fun DeckItem(deck: DeckEntity, cardCount: Int) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(1f), // Квадратная форма
    shape = RoundedCornerShape(8.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // Верхняя часть (пустая, можно добавить цвет или изображение)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .background(Color.LightGray)
      )

      // Нижняя часть с информацией
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
      ) {
        Text(
          text = deck.name, // Название деки
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
          text = "$cardCount карточек", // Количество карточек
          fontSize = 12.sp,
          color = Color.Gray,
          maxLines = 1
        )
      }
    }
  }
}