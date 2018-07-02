import scalaxb._

import com.github.alexanderfefelov.bgbilling.api.soap.kernel._

package object modules {

  def dr(key: String, value: String) = DataRecord(None, Some(key), value.toString)
  def dr(key: String, value: Int) = DataRecord(None, Some(key), value.toString)
  def dr(key: String, value: Double) = DataRecord(None, Some(key), value.toString)

}
