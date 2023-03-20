package shared.utils

import java.text.SimpleDateFormat
import java.time.{LocalDate, ZoneId}
import java.util.Date

object Utils {
  def convertDate(date: String): Date = {
    var formatDate = new SimpleDateFormat("yyyy-MM-dd")
    return formatDate.parse(date)
  }

  def getCurrentLocalDate: LocalDate = {
    LocalDate.now(ZoneId.systemDefault())
  }
}
