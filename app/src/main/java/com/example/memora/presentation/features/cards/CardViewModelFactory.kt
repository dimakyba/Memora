package com.example.memora.presentation.features.cards

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.memora.core.data.CardDao
import com.example.memora.core.data.DeckDao

class CardViewModelFactory(
    private val application: Application,
    private val cardDao: CardDao,
    private val deckDao: DeckDao
) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardViewModel(application, cardDao, deckDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
