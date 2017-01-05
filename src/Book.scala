package com.mangaz.project.publisher

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
    println(pages)
    pages
  }
}
