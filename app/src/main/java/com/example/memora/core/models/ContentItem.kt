package com.example.memora.core.models

sealed class ContentItem {
  data class Text(val text: String) : ContentItem()
  data class Image(val url: String) : ContentItem()
}