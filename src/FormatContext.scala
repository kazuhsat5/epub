package com.mangaz.project.publisher

object FormatContext {
  def publish(book: Book, format: Format): Boolean = {
    format.publish(book)
  }
}
