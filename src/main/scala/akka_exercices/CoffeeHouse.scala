package akka_exercices

import akka.actor.{Actor, ActorLogging, Props}
import akka_exercices.customer.DefaultCustomers
import akka_exercices.slow.SlowCoffeeMachine

object CoffeeHouse {

  // The customers send this messages
  case class OrderCoffee(customerName: String, coffee: String)

  // The customers expect this message as a reply
  case class ServeCoffee(customerName: String, coffee: Coffee)

  def props: Props = Props(new CoffeeHouse)
}

class CoffeeHouse
  extends Actor with ActorLogging {

  private val coffeeMachine = SlowCoffeeMachine(context.system)
  private val barrista = context.actorOf(Barrista.props(coffeeMachine), "barrista")
  private val waiter = context.actorOf(Waiter.props(barrista), "waiter")

  DefaultCustomers(waiter)(context.system)

  override def receive: Receive = PartialFunction.empty
}
