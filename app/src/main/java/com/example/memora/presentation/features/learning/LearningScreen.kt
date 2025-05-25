package com.example.memora.presentation.features.learning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.memora.core.algorithms.AdaptiveLearningFeedback
import com.example.memora.core.algorithms.AlgorithmType
import com.example.memora.core.algorithms.SimpleLearningFeedback
import com.example.memora.core.data.CardEntity
import com.example.memora.presentation.components.Component

class LearningScreen(
    private val card: CardEntity,
    private val onProcessFeedback: (com.example.memora.core.algorithms.LearningFeedback) -> Unit,
    private val onNavigateBack: () -> Unit
) : Component {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Display() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(card.name) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Go back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card content
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = card.content,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Feedback buttons based on algorithm type
                when (card.algorithm) {
                    AlgorithmType.ADAPTIVE -> {
                        // Adaptive algorithm feedback options
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { onProcessFeedback(AdaptiveLearningFeedback(5)) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary
                                )
                            ) {
                                Text("Easy")
                            }
                            Button(
                                onClick = { onProcessFeedback(AdaptiveLearningFeedback(3)) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Medium")
                            }
                            Button(
                                onClick = { onProcessFeedback(AdaptiveLearningFeedback(1)) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Hard")
                            }
                        }
                    }
                    AlgorithmType.FIXED_INTERVAL -> {
                        // Simple mark as learned button for fixed interval
                        Button(
                            onClick = { onProcessFeedback(SimpleLearningFeedback()) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null)
                                Text("Mark as Learned")
                            }
                        }
                    }
                }
            }
        }
    }
}
