package orders.domain

import java.time.LocalDateTime

case class Order(
  customerNameAndContact: String,
  shippingAddress: String,
  grandTotal: Double,
  dateWhenTheOrderWasPlaced: LocalDateTime,
  item: List[Item]
)
