package orders.domain

case class Item(
  cost: Double,
  shippingFee: Double,
  taxAmount: Double,
  product: Product
)
