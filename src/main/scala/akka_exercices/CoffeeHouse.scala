package akka_exercices

object CoffeeHouse {

  // The customers send this messages
  case class OrderCoffee(customerName: String, coffee: String)

  // The customers expect this message as a reply
  case class ServeCoffee(customerName: String, coffee: Coffee)

}

class CoffeeHouse {

}
