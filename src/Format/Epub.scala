package com.mangaz.project.publisher.format

import java.io.{File, PrintWriter}
import java.nio.file.{Files, StandardCopyOption}
import scala.xml.{XML, Elem}

import scala.collection.JavaConversions._

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.BufferedInputStream
import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry

/**
 * EPUB
 */
class Epub extends Format {

  /**
   * 出力
   *
   * @param book Bookオブジェクト
   * @return Boolean
   */
  def publish(book: Book): Boolean = {
    // 作業ディレクトリ作成
    val tmp: File = new File(book.uniqueId)
    tmp.mkdir

    // EPUBディレクトリ作成
    Array[String](
      "META-INF",
      "OEBPS",
      "OEBPS" + File.separator + "image",
      "OEBPS" + File.separator + "xhtml"
    ).foreach(each => {
      new File(tmp, each).mkdir
    })

    // 画像ファイルのコピー
    for (each <- book.getPages if each.isImage) {
      Files.copy(
        new File(each.getAbsolutePath).toPath,
        new File(tmp, Array("OEBPS", "image", each.getName).mkString(File.separator)).toPath,
        StandardCopyOption.REPLACE_EXISTING)
    }

    // 空白画像のコピー
    if (book.flyleaf == true) {  // 空白画像の挿入が必要な場合のみ
      Files.copy(
        new File("../resource/white.jpg").toPath,
        new File(tmp, "OEBPS/image/white.jpg").toPath,
        StandardCopyOption.REPLACE_EXISTING)
    }

    var xml: Elem = null

    /* ===================================================== */
    /*  mimetype                                             */
    /* ===================================================== */

    var file: File = new File(tmp, "mimetype")
    file.createNewFile

    var printWriter: PrintWriter = new PrintWriter(file)
    printWriter.write("application/epub+zip")
    printWriter.close

    /* ===================================================== */
    /*  container.xml                                        */
    /* ===================================================== */

    xml =
<container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container">
  <rootfiles>
    <rootfile full-path="OEBPS/content.opf" media-type="application/oebps-package+xml" />
  </rootfiles>
</container>

    XmlUtil.saveXml(Array(tmp.getAbsolutePath, "META-INF", "container.xml").mkString(File.separator), xml)

    /* ===================================================== */
    /*  content.opf                                          */
    /* ===================================================== */

    xml =
<package xmlns="http://www.idpf.org/2007/opf" version="3.0" xml:lang="ja" unique-identifier="unique-id" prefix="rendition: http://www.idpf.org/vocab/rendition/# ebpaj: http://www.ebpaj.jp/">
  <metadata xmlns:dc="http://purl.org/dc/elements/1.1/">
    <dc:title id="title">{book.title}</dc:title>
    <dc:language>ja</dc:language>
    <dc:identifier id="unique-id">{book.uniqueId}</dc:identifier>
    <meta property="dcterms:modified">2014-01-01T00:00:00Z</meta>
    <meta property="rendition:layout">pre-paginated</meta>
    <meta property="rendition:spread">landscape</meta>
  </metadata>
  <manifest>
    <item media-type="application/xhtml+xml" id="toc" href="toc.xhtml" properties="nav"/>
    {
      var count: Int = 0

      new File(tmp + File.separator + "OEBPS" + File.separator + "image").listFiles.map { each =>
        count += 1

        // 表紙画像
        if (count == 1) {
          <item media-type="image/jpeg" properties="cover-image" id={"i-" + FileSystemUtil.getNameWithoutExtension(each)} href={"image/" + each.getName} />
        // 表紙画像以外
        } else {
          <item media-type="image/jpeg" id={"i-" + FileSystemUtil.getNameWithoutExtension(each)} href={"image/" + each.getName} />
        }
      }
    }
    {
      new File(tmp + File.separator + "OEBPS" + File.separator + "image").listFiles.map { each =>
        val filename = FileSystemUtil.getNameWithoutExtension(each)

        <item media-type="application/xhtml+xml" id={"p-" + filename} href={"xhtml/" + filename + ".xhtml"} properties="svg"/>
      }
    }
  </manifest>
  <spine page-progression-direction={book.direction}>
    {
      var count: Int = 0

      new File(tmp + File.separator + "OEBPS" + File.separator + "image").listFiles.map { each =>
        count += 1

        val filename = FileSystemUtil.getNameWithoutExtension(each)

        // 表紙画像かつ白紙画像挿入なし
        if (count == 1 && book.flyleaf == false) {
          <itemref linear="yes" idref={"p-" + filename} properties="rendition:page-spread-center"/>
        // 表紙画像かつ白紙画像挿入あり
        } else if (count == 1 && book.flyleaf == true) {
          <itemref linear="yes" idref={"p-" + filename} properties="rendition:page-spread-center"/>
          <itemref linear="yes" idref="p-white" properties="page-spread-right"/>
        // その他のページ(白紙画像を除く)
        } else if (filename != "white") {
          // 奇数ページ
          if (count % 2 == 1) {
            // 白紙画像あり作品
            if (book.flyleaf == true) {
              <itemref linear="yes" idref={"p-" + filename} properties="page-spread-right" />
            // 白紙画像なし作品
            } else {
              <itemref linear="yes" idref={"p-" + filename} properties="page-spread-left" />
            }
          // 偶数ページ
          } else {
            // 白紙画像あり作品
            if (book.flyleaf == true) {
              <itemref linear="yes" idref={"p-" + filename} properties="page-spread-left" />
            // 白紙画像なし作品
            } else {
              <itemref linear="yes" idref={"p-" + filename} properties="page-spread-right" />
            }
          }
        }
      }
    }
  </spine>
</package>

    XmlUtil.saveXml(Array(tmp.getAbsolutePath, "OEBPS", "content.opf").mkString(File.separator), xml)

    /* ===================================================== */
    /*  toc.xhtml                                            */
    /* ===================================================== */

    xml =
//<!DOCTYPE html>
<html xmlns:epub="http://www.idpf.org/2007/ops" xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja">
  <head>
    <title>目次</title>
  </head>
  <body>
    <nav epub:type="toc">
      <h1>目次</h1>
      <ol>
      {
        var count: Int = 0

        new File(tmp + File.separator + "OEBPS" + File.separator + "image").listFiles.map { each =>
          val filename = FileSystemUtil.getNameWithoutExtension(each)

          count += 1

          // 白紙画像
          if (filename == "white") {
            // continue
          // 表紙画像
          } else if (count == 1) {
            <li><a href={"xhtml/" + filename + ".xhtml"}>表紙</a></li>
          // その他のページ
          } else {
            <li><a href={"xhtml/" + filename + ".xhtml"}>{filename}ページ</a></li>
          }
        }
      }
      </ol>
    </nav>
  </body>
</html>

    XmlUtil.saveXml(Array(tmp.getAbsolutePath, "OEBPS", "toc.xhtml").mkString(File.separator), xml)

    /* ===================================================== */
    /*  画像xhtml                                            */
    /* ===================================================== */

    new File(tmp + File.separator + "OEBPS" + File.separator + "image").listFiles.foreach(each => {
      val filename: String= FileSystemUtil.getNameWithoutExtension(each)
      xml =
//<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ja">
  <head>
    <meta name="viewport" content="width=848, height=1200"/>
    <title>{filename}</title>
  </head>
  <body>
    <div class="main">
      <svg xmlns="http://www.w3.org/2000/svg" version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink" width="100%" height="100%" viewBox="0 0 848 1200">
        <image width="848" height="1200" xlink:href={"../image/" + each.getName} />
      </svg>
    </div>
  </body>
</html>

      XmlUtil.saveXml(Array(tmp.getAbsolutePath, "OEBPS", "xhtml", filename + ".xhtml").mkString(File.separator), xml)
    });

    // Zipファイルの作成
    zipDir(book.uniqueId, book.uniqueId + ".zip")


    // ZIPファイル->EPUBファイル(拡張子の変更)
    FileSystemUtil.rename(book.uniqueId + ".zip", book.uniqueId + ".epub")

    true
  }





// @see https://www.ibm.com/developerworks/community/blogs/pgmrk/entry/epub_e3_81_ae_e9_9b_bb_e5_ad_90_e6_96_87_e6_9b_b8_e3_82_92_e4_bd_9c_e3_82_8b23?lang=en


  def zipDir(srcdir: String, outputfilename: String){
    var zos: ZipOutputStream = null

    val output: File = new File(outputfilename)

    zos = new ZipOutputStream(new FileOutputStream(output))

    archive(zos, srcdir + "/mimetype", srcdir)

    archive(zos, srcdir, srcdir)

    zos.close();
  }

  def archive(zos: ZipOutputStream, filename: String, srcdir: String) {
    val file: File = new File(filename);

    if (file.isDirectory == true) {
      val files: Array[File] = file.listFiles()

      for (f <- files) {
        archive(zos, f.getCanonicalPath, srcdir)
      }
    } else {
      if (filename.endsWith("mimetype") == false) {
        var fis: BufferedInputStream = null;

        fis = new BufferedInputStream(new FileInputStream(file));

        var entryName: String = file.getAbsolutePath

        val n: Int = entryName.indexOf(srcdir)

        if (n > -1) {
          entryName = entryName.substring(n + srcdir.length + 1)
        }

        entryName = entryName.replaceAll( "\\\\", "/" );

        zos.putNextEntry(new ZipEntry(entryName))

        var ava: Int = 0
        while ({ava = fis.available(); ava > 0}) {
          val bs: Array[Byte] = new Array[Byte](ava)

          fis.read(bs);

          zos.write(bs);
        }

        zos.closeEntry

        fis.close
      }
    }
  }

}
