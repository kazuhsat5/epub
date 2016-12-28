import com.mangaz.project.epub._
import java.io.File

object Client {
  def main(args: Array[String]): Unit = {
    val directory: String = args(0) // dev

    val book: Book = new Book
    for (file: File <- new File(directory).listFiles)
      book.addPage(new Page(file.getPath))

    book.publish
  }
}
