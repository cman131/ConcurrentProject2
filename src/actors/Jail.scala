package actors

/**
 * Kocsen Chung
 * actors.Jail, receives all criminals
 */

class Jail extends Actor {
  var captives = List()


  val log = Logging(context.system, this)

  def receive = {
    case SendPassenger(passenger) =>
      captives :+ passenger
      log.info("received passenger")
    case PoisonPill(kill) =>
      context.stop(self)
    case _ =>
      print("received unknown message")
  }
}
