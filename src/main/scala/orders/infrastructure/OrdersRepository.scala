package orders.infrastructure

import orders.domain._

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object OrdersRepository extends OrderRepositoryInterface {
  def getOrders: List[Order] = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val product1: Product = Product("Celular", "Dispositivos Móveis", 300.00, 1000.99, LocalDateTime.parse("2021-01-03 00:00:00", formatter))
    val product2: Product = Product("Tablet", "Dispositivos Móveis", 500.00, 2500.99, LocalDateTime.parse("2022-01-03 00:00:00", formatter))
    val product3: Product = Product("Fone de Ouvido", "Som Portátil", 50.00, 500.99, LocalDateTime.parse("2022-11-03 00:00:00", formatter))

    val item1: Item = Item(4524.67, 27.34, 39.93, product1)
    val item2: Item = Item(2195.12, 41.21, 12.31, product2)
    val item3: Item = Item(3388.23, 24.02, 6.36, product2)
    val item4: Item = Item(4364.34, 5.25, 7.96, product3)
    val item5: Item = Item(2190.45, 4.08, 34.63, product3)

    val listItem1: List[Item] = List(item1, item3, item5)
    val listItem2: List[Item] = List(item3)
    val listItem3: List[Item] = List(item1, item3, item4, item5)
    val listItem4: List[Item] = List(item2, item5)
    val listItem5: List[Item] = List(item1, item2, item3, item4, item5)

    val order1: Order = Order("Maria", "Rua 3328", 3169.56, LocalDateTime.parse("2021-01-03 00:00:00", formatter), listItem1)
    val order2: Order = Order("Luisa", "Rua 1391", 3169.56, LocalDateTime.parse("2021-01-03 00:00:00", formatter), listItem2)
    val order3: Order = Order("Diego", "Rua 3328", 3169.56, LocalDateTime.parse("2021-01-03 00:00:00", formatter), listItem3)
    val order4: Order = Order("Ana", "Rua 3328", 3169.56, LocalDateTime.parse("2021-01-03 00:00:00", formatter), listItem4)
    val order5: Order = Order("Flavio", "Rua 3328", 3169.56, LocalDateTime.parse("2022-01-03 00:00:00", formatter), listItem5)

    List(order1, order2, order3, order4, order5)
  }
}
