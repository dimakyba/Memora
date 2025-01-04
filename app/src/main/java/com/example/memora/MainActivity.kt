package com.example.memora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memora.ui.theme.MemoraTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MemoraTheme {
        MyApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
  Scaffold(topBar = {
    TopAppBar(title = {
      Text(
        text = "Memora", fontSize = 18.sp
      )
    })
  }, floatingActionButton = {
    FloatingActionButton(onClick = {

    /* Обработка клика */


    }) {
      Text("+")
    }
  }) { innerPadding ->
    Text(
      text = "Create your first Deck", modifier = Modifier
        .padding(innerPadding)
        .padding(16.dp)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
  MemoraTheme {
    MyApp()
  }
}
