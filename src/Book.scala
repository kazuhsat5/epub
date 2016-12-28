package com.mangaz.project.epub

import java.util.ArrayList

class Book {
  var title: String = "non-title"
  var uniqueId: String = "XXXXX-XXXXX-XXX"
  var flyleaf: Boolean = true
  var direction: String = "rtl"

  var pages: ArrayList[Page] = new ArrayList[Page]()

  def addPage(page: Page): Unit = {
    pages.add(page)
  }

  def getPages(): ArrayList[Page] = {
    pages
  }

  def publish(format: Format): Boolean = {
    format.publish(this)
  }
}
