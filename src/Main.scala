import akka.actor.{ActorSystem, Props}
/**
 * Created by Conor on 11/20/2014.
 * main entry point
 */
object Main {
  def main(args: Array[String]) {
    val NUM_PASSENGERS : Int  = 4

    /**
     * Actors to start
     * - Jail
     * - System
     * - Security
     * - Bagscan
     * - Bodyscan
     */
    val system = ActorSystem("mySystem")

    val jailActor = system.actorOf(Props[Jail])
    val systemActor = system.actorOf(Props(classOf[System], NUM_PASSENGERS))
    val securityActor = system.actorOf(Props(classOf[Security], jailActor, systemActor))

    val bagScannerActor = system.actorOf(Props[BagScanner])
    val bodyScannerActor = system.actorOf(Props[BodyScanner])



    println("Hello, world!")
  }
}