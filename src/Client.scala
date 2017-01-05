import com.mangaz.project.publisher._
import java.io.File

object Client {

  def main(args: Array[String]): Unit = {
    val directory: String = args(0) // 第一引数: ディレクトリ
    //val output: String = args(1)  // 第二引数: フォーマット
    val output: String = "epub" // EPUB3

    val book: Book = new Book(
      "non-title",  // 作品タイトル
      "XXXXX-XXXXX-XXX",  // ユニークID
      true, // 白紙画像挿入フラグ
      "rtl" // ページ方向
      )

    for (file: File <- new File(directory).listFiles)
      book.addPage(new Page(file.getPath))

    val format: Format =  output match {
      case "zip" => FormatFactory.create(FormatFactory.Type.ZIP)
      case "epub3" => FormatFactory.create(FormatFactory.Type.EPUB3)
    }

    format.publish(book)
  }

}
