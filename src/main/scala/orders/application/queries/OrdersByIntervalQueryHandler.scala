package orders.application.queries

import orders.domain.Order
import orders.infrastructure.OrderRepositoryInterface
import shared.utils.Utils

import scala.math.Ordered.orderingToOrdered
import java.time.{LocalDate, Period, ZoneId}

case class OrdersByIntervalQueryHandler(query: OrdersByIntervalQuery, ordersRepository: OrderRepositoryInterface) extends OrderByIntervalQueryHandlerInterface {

  private def filterOrderListByDate(orders: List[Order]): List[Order] = {
    orders.filter(order => order.dateWhenTheOrderWasPlaced >= query.initialDate && order.dateWhenTheOrderWasPlaced <= query.finalDate)
  }

  private def createEmptyIntervals(intervalList: List[List[String]]): Map[String, Int] = {
    intervalList.map(interval =>
      if (interval.head.isEmpty) (">" + interval(1), 0)
      else (interval.head + "-" + interval(1), 0)
    ).toMap
  }

  private def calculateMonthDifference(localFormatActualDay: LocalDate, productCreationDate: LocalDate): Long = {
    Period.between(localFormatActualDay, productCreationDate).toTotalMonths.abs
  }

  private def updateInterval(acc: Map[String, Int], monthDifference: Long, interval: List[String]): Map[String, Int] = {
    if (interval.head.isEmpty && monthDifference > interval(1).toInt) {
      acc.updated(">" + interval(1), acc(">" + interval(1)) + 1)
    } else if (interval.head.nonEmpty && monthDifference >= interval.head.toInt && monthDifference <= interval(1).toInt) {
      val key = interval.head + "-" + interval(1)
      acc.updated(key, acc(key) + 1)
    } else {
      acc
    }
  }

  private def generateResultMap(orders: List[Order], localFormatActualDay: LocalDate, intervalList: List[List[String]]): Map[String, Int] = {
    val emptyIntervals = createEmptyIntervals(intervalList)

    orders.flatMap(_.item).foldLeft(emptyIntervals) { (intervals, item) =>
      val productCreationDate = Utils.convertDate(item.product.creationDate.toString()).toInstant.atZone(ZoneId.systemDefault()).toLocalDate
      val monthDifference = calculateMonthDifference(localFormatActualDay, productCreationDate)

      intervalList.foldLeft(intervals) { (acc, interval) =>
        updateInterval(acc, monthDifference, interval)
      }
    }
  }

  private def processOrders(orders: List[Order], localFormatActualDay: LocalDate): Seq[(String, Int)] = {
    val filteredOrders = filterOrderListByDate(orders)
    val intervalList = query.resultKeyMaps.map(_.split("\\D+").toList)
    val intervals = generateResultMap(filteredOrders, localFormatActualDay, intervalList)
    intervals.toSeq.sortWith(_._1 < _._1)
  }

  override def handle: Seq[(String, Int)] = {
    val localFormatActualDay = Utils.getCurrentLocalDate
    val orders = ordersRepository.getOrders

    val result = processOrders(orders, localFormatActualDay)
    result
  }
}
