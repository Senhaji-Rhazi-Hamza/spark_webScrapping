package utils

import java.text.SimpleDateFormat
import java.util.Date

/**

  */
object DateUtils {

  val format = new SimpleDateFormat("yyyy-mm-dd")

  def StringToDate(str: String) : Date = format.parse(str)

}
