package com.mangaz.project.publisher

import scala.xml.{XML, Node}

/**
 * XmlUtil
 */
object XmlUtil {

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
