package com.mangaz.project.publisher

import java.io.File
import java.nio.file.{Path, Paths, Files, StandardCopyOption}

object FileSystemUtil {
  def mkdir(pathname: String): Boolean = {
    new File(pathname).mkdir
  }

  def deleteRecursive(directory: File): Boolean = {
    directory.listFiles.foreach(each => each.delete)

    directory.delete
  }

  def getNameWithoutExtension(file: File): String = {
    val name: String = file.getName()
    val index: Int = name.lastIndexOf('.')
    if (index != -1)
      return name.substring(0, index)

    ""
  }

  def getExtension(filename: String): String = {
    if (filename == null) return null

    val index: Int = filename.lastIndexOf(".")
    if (index != -1) {
        return filename.substring(index + 1);
    }

    filename;
  }

  def getExtension(file: File): String = {
    val filename: String = file.getName

    getExtension(filename)
  }

  def rename(oldname: String, newname: String): Path = {
    Files.move(Paths.get(oldname), Paths.get(newname), StandardCopyOption.REPLACE_EXISTING)
  }
}
