package akka_exercices

import akka.actor.{ActorRef, ActorSystem}
import akka_exercices.customer.DefaultCustomers
import com.typesafe.config.ConfigFactory

object CoffeeApplication extends App {

  val config = ConfigFactory.load()
  implicit val system: ActorSystem = ActorSystem("CoffeeApp", config)

  val coffeeHouse: ActorRef = ???
}
