package driver

import actors._
import akka.actor.{ActorSystem, Props}
import messages.Setup

/**
 * Created by Conor on 11/20/2014.
 * main entry point.
 * Will create actor system, and actors with appropriate configuration.
 */
object Main {
  def main(args: Array[String]) {
    val NUM_PASSENGERS: Int = 4
    val NUM_LINES: Int = 4

    /**
     * Actors to start
     * - actors.Jail
     * - actors.System
     * - actors.Security
     * - Bagscan
     * - Bodyscan
     * - Queue Actor
     * -
     */
    val system = ActorSystem("mySystem")

    val jailActor = system.actorOf(Props[actors.Jail])
    val systemActor = system.actorOf(Props(classOf[System], NUM_PASSENGERS))

    for (a <- 1 to NUM_LINES) {
      println("Creating line: " + a)
      val securityActor = system.actorOf(Props(classOf[Security], jailActor, systemActor, a))
      val bagScannerActor = system.actorOf(Props[BagScanner]) // need messages.Setup msg
      val bodyScannerActor = system.actorOf(Props[BodyScanner]) // need messages.Setup msg
      val queueActor = system.actorOf(Props[LineQueue]) // need messages.Setup msg
      val setupMsg = Setup(queueActor, securityActor, bodyScannerActor, bagScannerActor)

      bagScannerActor ! setupMsg
      bodyScannerActor ! setupMsg
      bodyScannerActor ! setupMsg
    }

    val documentCheckActor = system.actorOf(Props[DocumentCheck]) // need messages.Setup msg



    println("Hello, world!")
  }
}