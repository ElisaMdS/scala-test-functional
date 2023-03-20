package orders.application.queries

import java.time.LocalDateTime

case class OrdersByIntervalQuery(initialDate: LocalDateTime, finalDate: LocalDateTime, resultKeyMaps: List[String])
