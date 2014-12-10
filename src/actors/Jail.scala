package actors

/**
 * Kocsen Chung
 * actors.Jail, receives all criminals
 */

import akka.actor.{ Actor, ActorSystem }
import messages._

class Jail(sys : ActorSystem) extends Actor {
  var captives = List()

  /**
   * Receives a passsenger and puts im in the captives Array List.
   *
   * At the end of the day receives a poison pill, stops the system and then itself.
   * @return N/A
   */
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
