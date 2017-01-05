package com.mangaz.project.publisher

import java.util.ArrayList

/**
 * 書籍
 *
 * @param title 作品タイトル
 * @param uniqueId ユニークID
 * @param flyleaf 扉存在書籍(白紙画像フラグ)
 * @param direction ページ方向
 */
class Book(
  val title: String,
  val uniqueId: String,
  val flyleaf: Boolean,
  val direction: String
  ) {

  var pages: ArrayList[Page] = new ArrayList[Page]()

  /**
   * ページを追加する
   *
   * @param page ページオブジェクト
   */
  def addPage(page: Page): Unit = {
    pages.add(page)
  }

  /**
   * すべてのページを取得する
   *
   * @return ページオブジェクト配列
   */
  def getPages(): ArrayList[Page] = {
    println(pages)
    pages
  }

}
