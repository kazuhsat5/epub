package com.mangaz.project.epub

import java.io.File

class Page(pathname: String) {

  val file = new File(pathname)

  def getNameExtractExtension(): String = {
    val filename = file.getName

    val point: Int = filename.lastIndexOf(".");
    if (point == -1)
        return null

    filename.substring(0, point);
  }

}
