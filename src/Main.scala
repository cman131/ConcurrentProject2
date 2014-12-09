import actors._
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
     * - actors.Jail
     * - actors.System
     * - actors.Security
     * - Bagscan
     * - Bodyscan
     */
    val system = ActorSystem("mySystem")

    val jailActor = system.actorOf(Props[Jail])
    val systemActor = system.actorOf(Props(classOf[System], NUM_PASSENGERS))
    val securityActor = system.actorOf(Props(classOf[Security], jailActor, systemActor))

    val bagScannerActor = system.actorOf(Props[BagScanner]) // need messages.Setup msg
    val bodyScannerActor = system.actorOf(Props[BodyScanner]) // need messages.Setup msg

    val queueActor = system.actorOf(Props[Queue]) // need messages.Setup msg

    val documentCheckActor = system.actorOf(Props[DocumentCheck]) // need messages.Setup msg



    println("Hello, world!")
  }
}