import com.mangaz.project.publisher
import java.io.File

object Client {
  def main(args: Array[String]): Unit = {
    val directory: String = args(0) // dev
    val output: String = "epub"   // defalt

    val book: Book = new Book
    for (file: File <- new File(directory).listFiles)
      book.addPage(new Page(file.getPath))

    val format: Format =  output match {
      case "epub" => format = FormatFactory.create(FormatType.EPUB)
    }

    format.publish(book)
  }
}
