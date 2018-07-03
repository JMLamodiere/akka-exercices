package akka_exercices

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka_exercices.Barrista.PrepareCoffee
import akka_exercices.CoffeeHouse.OrderCoffee

object Waiter {

  def props(barrista: ActorRef): Props = Props(new Waiter(barrista))
}

class Waiter(barrista: ActorRef)
  extends Actor with ActorLogging {

  val knowCoffees = Set("akkaccino", "mochaplay", "caffescala")

  val maxOffenses = 3
  var offenses: Int = 0

  override def receive: Receive = {
    case OrderCoffee(customerName, coffeeName) if knowCoffees.contains(coffeeName.toLowerCase) =>
      log.debug(s"Waiter takes order of a $coffeeName for $customerName")
      barrista forward PrepareCoffee(customerName, coffeeName)

    case other: Any if offenses < maxOffenses =>
      offenses += 1

    case other: Any =>
      log.warning(s"Waiter is offended !!")
      context become offended
  }

  def offended: Receive = {
    case any: Any =>
      sender() ! "Vai a quel paese !"
  }
}
