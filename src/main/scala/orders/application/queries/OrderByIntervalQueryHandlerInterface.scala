package orders.application.queries

trait OrderByIntervalQueryHandlerInterface {
  
  def handle: Seq[(String, Int)]

}
