package com.example.memora.core.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.memora.core.data.AppDatabase
import com.example.memora.core.data.CardDao
import com.example.memora.core.data.DeckDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewNotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val notificationHelper = NotificationHelper(context)
    private val cardDao: CardDao = AppDatabase.getInstance(context).cardDao()
    private val deckDao: DeckDao = AppDatabase.getInstance(context).deckDao()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val cardId = inputData.getLong("cardId", -1)
            if (cardId == -1L) return@withContext Result.failure()

            val card = cardDao.getCardById(cardId) ?: return@withContext Result.failure()
            val deck = deckDao.getDeckById(card.deckId) ?: return@withContext Result.failure()

            notificationHelper.showReviewNotification(
                cardId = card.id,
                cardName = card.name,
                deckName = deck.name
            )

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val WORK_NAME = "review_notification_worker"
    }
}
