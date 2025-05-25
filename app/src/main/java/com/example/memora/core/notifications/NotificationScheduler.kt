package com.example.memora.core.notifications

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class NotificationScheduler(private val context: Context) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleReviewNotification(cardId: Long, delayMillis: Long) {
        val inputData = Data.Builder()
            .putLong("cardId", cardId)
            .build()

        val notificationWork = OneTimeWorkRequestBuilder<ReviewNotificationWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        workManager.enqueueUniqueWork(
            "${ReviewNotificationWorker.WORK_NAME}_$cardId",
            ExistingWorkPolicy.REPLACE,
            notificationWork
        )
    }

    fun cancelReviewNotification(cardId: Long) {
        workManager.cancelUniqueWork("${ReviewNotificationWorker.WORK_NAME}_$cardId")
    }
}
