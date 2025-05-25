package com.example.memora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.memora.core.data.AppDatabase
import com.example.memora.navigation.NavController
import com.example.memora.presentation.features.cards.CardViewModel
import com.example.memora.presentation.features.cards.CardViewModelFactory
import com.example.memora.presentation.features.deck.DeckViewModel
import com.example.memora.ui.theme.MemoraTheme

class MainActivity : ComponentActivity() {
    private lateinit var cardViewModel: CardViewModel
    private lateinit var deckViewModel: DeckViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(applicationContext)
        val cardDao = database.cardDao()
        val deckDao = database.deckDao()

        val cardViewModelFactory = CardViewModelFactory(application, cardDao, deckDao)
        cardViewModel = ViewModelProvider(this, cardViewModelFactory)[CardViewModel::class.java]
        deckViewModel = DeckViewModel(deckDao)

        setContent {
            MemoraTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavController(
                        navController = navController,
                        deckViewModel = deckViewModel,
                        cardViewModel = cardViewModel
                    ).AppNavigation()
                }
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
