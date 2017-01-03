package com.mangaz.project.publisher

object FormatFactory {
  val EPUB: String = "epub"

  def create(format: FormatType.Value): Format = {
    format match {
      case FormatType.EPUB => new Epub
      case FormatType.ZIP => new Zip
      case _ => null
    }
  }
}
