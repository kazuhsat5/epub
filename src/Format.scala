package com.mangaz.project.publisher

trait Format {
  def publish(book: Book): Boolean;
}
