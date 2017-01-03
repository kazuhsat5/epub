package com.mangaz.project.publisher

import java.util.zip.{ZipOutputStream, ZipEntry}
import java.io.{FileOutputStream, FileInputStream}

class Zip extends Format {
  def publish(book: Book): Boolean = {
    val zipOutputStream: ZipOutputStream = new ZipOutputStream(new FileOutputStream(book.uniqueId + ".zip"))

    book.getPages.forEach(each => {
      val fileInputStream: FileInputStream = new FileInputStream(each.getAbsolutePath)

      zipOutputStream.putNextEntry(new ZipEntry(each.getName))

      var bytes: Array[Byte] = new Array[Byte](10240000)  // 10M
      var length: Int = 0
      while ({length = fileInputStream.read(bytes); length > 0}) {
        zipOutputStream.write(bytes, 0, length)
      }

      fileInputStream.close
    })

    zipOutputStream.close

    true
  }
}
