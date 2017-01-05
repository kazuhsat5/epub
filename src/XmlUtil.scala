package com.mangaz.project.publisher

import scala.xml.{XML, Node}

object XmlUtil {
  def saveXml(pathname: String, xml: Node): Boolean = {
    println(XML.save(pathname, xml, "UTF-8", true))

    true
  }
}
