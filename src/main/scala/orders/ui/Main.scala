package orders.ui

import orders.application.queries.{OrdersByIntervalQuery, OrdersByIntervalQueryHandler}
import orders.infrastructure.OrdersRepository

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Main {

  private def parseArgs(args: Array[String]): OrdersByIntervalQuery = {
    if (args.length < 2) {
      println("Usage: program <initial_date> <final_date> <result_map_keys>")
      sys.exit(1)
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    val initialDateTime = LocalDateTime.parse(args(0), formatter)
    val finalDateTime = LocalDateTime.parse(args(1), formatter)
    val resultMapKeys =
      if (args.length == 2) List("1-3", "4-6", "7-12", ">12")
      else args.drop(2).toList

    OrdersByIntervalQuery(initialDateTime, finalDateTime, resultMapKeys)
  }

  private def printResults(results: Seq[(String, Int)]): Unit = {
    results.foreach { case (key, value) => println(s"$key months: $value orders") }
  }

  def main(args: Array[String]): Unit = {
    val query = parseArgs(args)
    val result = OrdersByIntervalQueryHandler(query, OrdersRepository).handle
    printResults(result)
  }
}
