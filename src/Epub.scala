package com.mangaz.project.publisher

import java.io.{File, PrintWriter}
import java.nio.file.{Files, StandardCopyOption}
import scala.xml.XML

class Epub extends Format {
  def publish(book: Book): Boolean = {
    val tmp: File = new File(book.uniqueId)

    tmp.mkdir

    Array[String](
      "META-INF",
      "OEBPS",
      "OEBPS" + File.separator + "images",
      "OEBPS" + File.separator + "xhtml"
    ).foreach(each => {
      new File(tmp, each).mkdir
    })

    book.getPages.forEach(each => {
      val from = new File(each.getAbsolutePath).toPath
      val to = new File(tmp, Array("OEBPS", "images", each.getName).mkString(File.separator)).toPath
      Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING)
    })

    // mimetypeファイル作成
    var file: File = new File(tmp, "mimetype")
    file.createNewFile

    var printWriter: PrintWriter = new PrintWriter(file)
    printWriter.write("application/epub+zip")
    printWriter.close

    // container.xml作成
    var xml =
      <container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container">
        <rootfiles>
          <rootfile full-path="OEBPS/content.opf" media-type="application/oebps-package+xml" />
        </rootfiles>
      </container>

    XmlUtil.saveXml(Array(tmp.getAbsolutePath, "META-INF", "container.xml").mkString(File.separator), xml)

    // content.opf作成

    // toc.xhtml作成

    // コンテンツxhtml作成

    // Zipファイルの作成

    // 拡張子の変更

    //FileSystemUtil.deleteRecursive(tmp)

    true
  }
}
