package com.mangaz.project.publisher

import com.mangaz.project.publisher.format.{Zip, Epub}

/**
 * フォーマットファクトリ
 *
 */
object FormatFactory {

  /**
   * フォーマット形式
   *
   */
  object Type extends Enumeration {

    val EPUB, ZIP = Value

  }

  /**
   * フォーマットオブジェクトを生成する
   *
   * @param format フォーマット
   * @return Formatオブジェクト
   */
  def create(format: Type.Value): Format = {
    format match {
      case Type.EPUB => new Epub
      case Type.ZIP => new Zip
      case _ => null
    }
  }

}
