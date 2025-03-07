package com.example.memora

import DeckListScreen
import DeckViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.memora.data.AppDatabase
import com.example.memora.ui.theme.MemoraTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val database = Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java, "memora-db"
    ).build()

    // Получаем DeckDao из базы данных
    val deckDao = database.deckDao()

    // Инициализация ViewModel
    val deckViewModel = DeckViewModel(deckDao)

    setContent {
      MemoraTheme {
//        MyApp()
        DeckListScreen(viewModel = deckViewModel)
      }
    }
  }
}


//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MyApp() {
//  Scaffold(topBar = {
//    TopAppBar(title = {
//      Text(
//        text = "Memora", fontSize = 18.sp
//      )
//    })
//  }, floatingActionButton = {
//    FloatingActionButton(onClick = {
//
//    /* Обработка клика */
//
//
//    }) {
//      Text("+")
//    }
//  }) { innerPadding ->
//    Text(
//      text = "Create your first Deck", modifier = Modifier
//        .padding(innerPadding)
//        .padding(16.dp)
//    )
//  }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun MyAppPreview() {
//  MemoraTheme {
//    MyApp()
//  }
//}
