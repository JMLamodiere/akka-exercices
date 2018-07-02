package akka_exercices

sealed trait Coffee

case object Akkaccino extends Coffee
case object MochaPlay extends Coffee
case object CaffeScala extends Coffee

case class NoSuchCoffeeException(name: String)
  extends Exception(s"There is no such coffee like '$name'")
