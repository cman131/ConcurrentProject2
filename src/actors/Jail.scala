package actors

/**
 * Kocsen Chung
 * actors.Jail, receives all criminals
 */

import akka.actor.Actor
import akka.event.Logging
import messages._

class Jail extends Actor {
  var captives = List()


  val log = Logging(context.system, this)

  def receive = {
    case SendPassenger(passenger, true) =>
      captives :+ passenger
      log.info("received passenger")
    case PoisonPill(kill) =>
      context.stop(self)
    case _ =>
      print("received unknown message")
  }
}
