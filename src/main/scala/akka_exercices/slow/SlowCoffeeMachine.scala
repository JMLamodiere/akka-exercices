package akka_exercices.slow

import akka.actor.ActorSystem
import akka_exercices._

import scala.concurrent.{Await, ExecutionContext, Promise}
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class SlowCoffeeMachine(implicit system: ActorSystem) {

  def prepareCoffee(name: String): Try[Coffee] = name.toLowerCase match {
    case "akkaccino" => slow(10.seconds)(Success(Akkaccino))
    case "mochaplay" => slow(10.second)(Success(MochaPlay))
    case "caffescala" => slow(15.seconds)(Success(CaffeScala))
    case other: String => slow(20.seconds)(Failure(NoSuchCoffeeException(other)))
  }

  private implicit val ec: ExecutionContext = system.dispatcher

  private def slow(duration: FiniteDuration)(result: Try[Coffee]): Try[Coffee] = {
    val promise = Promise[Coffee]()
    system.scheduler.scheduleOnce(duration)(promise.complete(result))
    Try(Await.result(promise.future, duration + 1.second))
  }
}

object SlowCoffeeMachine {

  def apply(system: ActorSystem): SlowCoffeeMachine = new SlowCoffeeMachine()(system)
}
