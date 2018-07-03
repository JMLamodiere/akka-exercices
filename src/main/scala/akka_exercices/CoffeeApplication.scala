package akka_exercices

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.config.ConfigFactory

object CoffeeApplication extends App {

  val config = ConfigFactory.load()
  implicit val system: ActorSystem = ActorSystem("CoffeeApp", config)

  val coffeeHouse: ActorRef = system.actorOf(CoffeeHouse.props, "house")
}
