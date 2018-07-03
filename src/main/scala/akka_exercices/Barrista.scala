package akka_exercices

import akka.actor.{Actor, ActorLogging, Props}
import akka_exercices.Barrista.PrepareCoffee
import akka_exercices.CoffeeHouse.ServeCoffee
import akka_exercices.slow.SlowCoffeeMachine

import scala.util.{Failure, Success}

object Barrista {

  case class PrepareCoffee(customerName: String, coffeeName: String)

  def props(coffeeMachine: SlowCoffeeMachine): Props = Props(new Barrista(coffeeMachine))
}

class Barrista(coffeeMachine: SlowCoffeeMachine)
  extends Actor with ActorLogging {

  override def receive: Receive = {
    case PrepareCoffee(customerName, coffeeName) =>
      log.debug(s"Preparing coffeee $coffeeName for $customerName")
      coffeeMachine.prepareCoffee(coffeeName) match {
        case Success(coffee) => sender() ! ServeCoffee(customerName, coffee)
        case Failure(error: NoSuchCoffeeException) => sender() ! error
        case Failure(error) => throw error
      }
  }
}
