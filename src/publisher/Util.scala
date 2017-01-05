package com.mangaz.project.publisher

import java.io.File
import java.nio.file.{Path, Paths, Files, StandardCopyOption}
import scala.xml.{XML, Node}

/**
 * ユーティリティ
 */
object Util {

  /**
   * ディレクトリを作成する
   *
   * @param pathname ディレクトリのパス
   * @return 実行結果(trueで成功)
   */
  def mkdir(pathname: String): Boolean = {
    new File(pathname).mkdir
  }

  /**
   * ディレクトリ・ファイルの再帰的削除を行う
   *
   * @param directory 削除するディレクトリのパス
   * @return 実行結果(trueで成功)
   */
  def deleteRecursive(directory: File): Boolean = {
    directory.listFiles.foreach(each => each.delete)

    directory.delete
  }

  /**
   * 拡張子を除外したファイル名を取得する
   *
   * @param file Fileオブジェクト
   * @return 拡張子を除外したファイル名
   */
  def getNameWithoutExtension(file: File): String = {
    val name: String = file.getName()
    val index: Int = name.lastIndexOf('.')
    if (index != -1)
      return name.substring(0, index)

    ""
  }

  /**
   * 拡張子を取得する
   *
   * @param filename ファイル名文字列
   * @return 拡張子文字列
   */
  def getExtension(filename: String): String = {
    if (filename == null) return null

    val index: Int = filename.lastIndexOf(".")
    if (index != -1) {
        return filename.substring(index + 1);
    }

    filename;
  }

  /**
   * 拡張子を取得する
   *
   * @param file Fileオブジェクト
   * @return 拡張子文字列
   */
  def getExtension(file: File): String = {
    val filename: String = file.getName

    getExtension(filename)
  }

  /**
   * ファイル名を変更する
   *
   * @param oldname 変更前のファイル名(ファイルパス)
   * @param newname 変更後のファイル名(ファイルパス)
   * @return Pathオブジェクト
   */
  def rename(oldname: String, newname: String): Path = {
    Files.move(Paths.get(oldname), Paths.get(newname), StandardCopyOption.REPLACE_EXISTING)
  }

  /**
   * XMLをファイルに保存(出力)する
   *
   * @param pathname 出力するファイルのパス
   * @param xml XML
   * @return 出力結果(trueで成功)
   */
  def saveXml(pathname: String, xml: Node): Boolean = {
    println(XML.save(pathname, xml, "UTF-8", true))

    true
  }

}
