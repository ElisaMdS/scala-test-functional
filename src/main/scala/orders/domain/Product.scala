package orders.domain

import java.time.LocalDateTime

case class Product(
  name: String,
  category: String,
  weight: Double,
  price: Double,
  creationDate: LocalDateTime
)

