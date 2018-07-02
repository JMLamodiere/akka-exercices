package akka_exercices.customer

import akka.actor.{ActorRef, ActorSystem}

import scala.concurrent.duration._

object DefaultCustomers {

  def apply(house: ActorRef)(implicit system: ActorSystem): DefaultCustomers =
    new DefaultCustomers(house)
}

class DefaultCustomers(house: ActorRef)(implicit system: ActorSystem) {

  val names = List("john", "jack", "mary", "anne", "roman", "pedro", "irene", "carl")
  system.actorOf(ScheduledCustomer.props(names, "akkaccino", 3.seconds, house))
  system.actorOf(ScheduledCustomer.props(names, "mochaplay", 5.seconds, house))
  system.actorOf(ScheduledCustomer.props(names, "caffescala", 4.seconds, house))
}
