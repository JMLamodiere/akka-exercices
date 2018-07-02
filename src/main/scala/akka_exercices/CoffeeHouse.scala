package akka_exercices

object CoffeeHouse {

  case class OrderCoffee(customerName: String, coffee: String)
  case class ServeCoffee(customerName: String, coffee: Coffee)

}

class CoffeeHouse {

}
