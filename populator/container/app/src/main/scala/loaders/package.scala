import java.nio.charset.Charset

package object loaders {

  implicit val charset: Charset = Charset.forName("UTF-8")

}
