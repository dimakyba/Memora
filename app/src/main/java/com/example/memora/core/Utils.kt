package com.example.memora.core

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {
  companion object {
    fun formatTimeMillis(millis: Long): String {
      val date = Date(millis)
      val format = SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault())
      return format.format(date)
    }
  }
}