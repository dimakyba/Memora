import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memora.data.DeckDao
import com.example.memora.data.DeckEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeckViewModel(private val deckDao: DeckDao) : ViewModel() {

  // Используем StateFlow вместо LiveData
  private val _decks = MutableStateFlow<List<DeckEntity>>(emptyList())
  val decks: StateFlow<List<DeckEntity>> = _decks

  init {
    getAllDecks() // Получаем все деки при старте
  }

  private fun getAllDecks() {
    viewModelScope.launch {
      _decks.value = deckDao.getAllDecks() // Обновляем данные
    }
  }

  fun addDeck(name: String) {
    viewModelScope.launch {
      val newDeck = DeckEntity(name = name)
      deckDao.insertDeck(newDeck) // Добавляем новую деку в базу
      getAllDecks() // Перезагружаем список дек
    }
  }

  suspend fun getCardCountForDeck(deckId: Long): Int {
    return deckDao.getCardCountForDeck(deckId)
  }
}