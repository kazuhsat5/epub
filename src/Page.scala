package com.mangaz.project.publisher

import java.io.File

class Page(pathname: String) extends File(pathname: String) {
  def getNameExtractExtension(): String = {
    val filename = getName

    val point: Int = filename.lastIndexOf(".");
    if (point == -1)
        return null

    filename.substring(0, point);
  }
}
