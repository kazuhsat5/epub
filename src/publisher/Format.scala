package com.mangaz.project.publisher

/**
 * Format
 */
trait Format {

  /**
   * 出力
   *
   * @param book Bookオブジェクト
   * @return 変換結果(trueで成功)
   */
  def publish(book: Book): Boolean;

}
