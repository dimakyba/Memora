package com.example.memora.domain.models

data class Content(val items: List<ContentItem>)

sealed class ContentItem {
  data class Text(val text: String) : ContentItem()
  data class Image(val url: String) : ContentItem()
}

//val content = Content(
//  listOf(
//    ContentItem.Text("example of text"),
//    ContentItem.Image("https://example.com/image.jpg"),
//    ContentItem.Text("one more text")
//  )
//)

