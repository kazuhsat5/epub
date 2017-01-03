package com.mangaz.project.publisher

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import java.io.FileOutputStream
import java.io.FileInputStream

class Epub extends Format {
  def publish(book: Book): Boolean = {
    val tmp: File = new File(book.uniqueId)

    tmp.mkdir

    book.getPages.forEach(each => {
        Files.copy(new File(each.getAbsolutePath).toPath, new File(book.uniqueId + File.separator + each.getName).toPath, StandardCopyOption.REPLACE_EXISTING)
    })


    // EPUB出力

    // 作業ディレクトリの削除
    tmp.listFiles.foreach(each => each.delete)

    true
  }
}
