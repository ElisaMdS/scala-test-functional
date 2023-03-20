package orders.infrastructure

import orders.domain.Order

trait OrderRepositoryInterface {
  def getOrders: List[Order]
}
