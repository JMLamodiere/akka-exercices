package akka_exercices.customer

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka_exercices.{Akkaccino, CaffeScala, Coffee, MochaPlay}
import akka_exercices.CoffeeHouse.{OrderCoffee, ServeCoffee}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration
import scala.util.Random

object ScheduledCustomer {

  def props(names: List[String], wantedCoffee: String, interval: FiniteDuration, house: ActorRef): Props =
    Props(new ScheduledCustomer(names, wantedCoffee, interval, house))
}

class ScheduledCustomer(names: List[String], wantedCoffee: String, interval: FiniteDuration, house: ActorRef)
  extends Actor with ActorLogging {

  implicit val ex: ExecutionContext = context.system.dispatcher
  context.system.scheduler.schedule(interval, interval, house, OrderCoffee(pickName(), wantedCoffee))

  override def receive: Receive = {
    case ServeCoffee(name, Akkaccino) if wantedCoffee == "akkaccino" => happy(name, Akkaccino)
    case ServeCoffee(name, MochaPlay) if wantedCoffee == "mochaplay" => happy(name, MochaPlay)
    case ServeCoffee(name, CaffeScala) if wantedCoffee == "caffeescala" => happy(name, CaffeScala)
    case ServeCoffee(name, coffee) => wrong(name, coffee)
  }

  private def happy(name: String, coffee: Coffee): Unit =
    log.info(s"Customer $name happily received his $coffee")

  private def wrong(name: String, coffee: Coffee): Unit =
    log.error(s"Customer $name mistakenly received a $coffee and leaves sad")

  private def pickName(): String = names(Random.nextInt(names.size - 1))
}
