package com.mangaz.project.publisher

import java.io.File

object FileSystemUtil {
  def mkdir(pathname: String): Boolean = {
    new File(pathname).mkdir
  }

  def deleteRecursive(directory: File): Boolean = {
    directory.listFiles.foreach(each => each.delete)

    directory.delete
  }
}
