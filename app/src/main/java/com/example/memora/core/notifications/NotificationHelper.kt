package com.example.memora.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.memora.MainActivity
import com.example.memora.R

class NotificationHelper(private val context: Context) {
  companion object {
    const val CHANNEL_ID = "memora_review_channel"
    const val CHANNEL_NAME = "Card Reviews"
    const val CHANNEL_DESCRIPTION = "Notifications for card review reminders"
  }

  private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

  init {
    createNotificationChannel()
  }

  private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_DEFAULT
      ).apply {
        description = CHANNEL_DESCRIPTION
      }
      notificationManager.createNotificationChannel(channel)
    }
  }

  fun showReviewNotification(cardId: Long, cardName: String, deckName: String) {
    val intent = Intent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      putExtra("cardId", cardId)
    }

    val pendingIntent = PendingIntent.getActivity(
      context,
      cardId.toInt(),
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_notification) // You'll need to add this icon
      .setContentTitle("Час повторити картку")
      .setContentText("Прийшов час повторити \"$cardName\" із деки \"$deckName\"")
      .setPriority(NotificationCompat.PRIORITY_DEFAULT)
      .setAutoCancel(true)
      .setContentIntent(pendingIntent)
      .build()

    notificationManager.notify(cardId.toInt(), notification)
  }
}
