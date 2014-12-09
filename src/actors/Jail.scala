package actors

/**
 * Kocsen Chung
 * actors.Jail, receives all criminals
 */

import akka.actor.Actor
import messages._

class Jail extends Actor {
  var captives = List()


  def receive = {
    case SendPassenger(passenger, true) =>
      captives :+ passenger
      println("Received passenger, sending to jail")
    case PoisonPill(kill) =>
      println("All passengers are going to permanent detention")
      context.stop(self)
    case _ =>
      print("received unknown message")
  }
}
