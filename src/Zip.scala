package com.mangaz.project.publisher

//import java.io.File
//import java.nio.file.Files
//import java.nio.file.StandardCopyOption
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import java.io.FileOutputStream
import java.io.FileInputStream

class Zip extends Format {
  def publish(book: Book): Boolean = {
    val zipOutputStream: ZipOutputStream = new ZipOutputStream(new FileOutputStream(book.uniqueId + ".zip"))

    val BUFFER_SIZE: Int = 10240000
    var bytes: Array[Byte] = new Array[Byte](BUFFER_SIZE)
    var length: Int = 0

    book.getPages.forEach(each => {
      val path = each.getAbsolutePath

      val fileInputStream: FileInputStream = new FileInputStream(path)

      zipOutputStream.putNextEntry(new ZipEntry(each.getName))

      while ({length = fileInputStream.read(bytes); length > 0}) {
        zipOutputStream.write(bytes, 0, length)
      }

      fileInputStream.close
    })

    zipOutputStream.close

    true
  }
}
