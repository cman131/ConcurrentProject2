package actors

/**
 * Kocsen Chung
 * actors.Jail, receives all criminals
 */

import akka.actor.{ Actor, ActorSystem }
import messages._

class Jail(sys : ActorSystem) extends Actor {
  var captives = List()


  def receive = {
    case SendPassenger(passenger, true, false) =>
      captives :+ passenger
      println("Jail: SendPassenger() -> Received passenger")
    case PoisonPill(kill) =>
      println("Jail: PoisonPill -> All passengers are going to permanent detention, shutting down")
      sys.shutdown()
      context.stop(self)
    case _ =>
      print("Jail: Received unknown message")
  }
}
