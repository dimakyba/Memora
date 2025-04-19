package com.example.memora


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.rememberNavController
import com.example.memora.core.data.AppDatabase
import com.example.memora.navigation.NavController
import com.example.memora.presentation.features.cards.CardViewModel
import com.example.memora.presentation.features.deck.DeckViewModel
import com.example.memora.ui.theme.MemoraTheme

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val database = AppDatabase.getInstance(applicationContext)
    val deckDao = database.deckDao()
    val cardDao = database.cardDao()

    val deckViewModel = DeckViewModel(deckDao)
    val cardViewModel = CardViewModel(cardDao, deckDao)

    setContent {
      MemoraTheme {
        val navController = rememberNavController()

        NavController(
          navController = navController,
          deckViewModel = deckViewModel,
          cardViewModel = cardViewModel
        ).AppNavigation()
      }
    }

  }


//  override fun onRequestPermissionsResult(
//    requestCode: Int,
//    permissions: Array<out String>,
//    grantResults: IntArray
//  ) {
//    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
//      if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//        // Permission granted
//      } else {
//        // Permission denied
//      }
//    }
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
