package com.mangaz.project.publisher

import java.io.File

/**
 * ページ
 *
 * @param pathname ファイルパス
 */
class Page(pathname: String) extends File(pathname: String) {

  /**
   * 拡張子を除いたファイル名を取得する
   *
   * @return 拡張子を除いたファイル名
   */
  def getNameExtractExtension(): String = {
    val filename = getName

    val point: Int = filename.lastIndexOf(".");
    if (point == -1)
        return null

    filename.substring(0, point);
  }

  /**
   * 画像ファイルか判定を行う
   *
   * @return 判定結果
   */
  def isImage(): Boolean = {
    // 拡張子「.jpeg」あるいは「.jpg」のみを認める
    List("jpeg", "jpg").contains(FileSystemUtil.getExtension(getName))
  }

}
