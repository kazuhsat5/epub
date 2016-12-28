package com.mangaz.project.epub

trait Format {
  def publish(book: Book): Boolean
}
