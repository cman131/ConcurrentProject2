package driver

import actors._
import akka.actor.{ActorSystem, ActorRef, Props}
import messages.{SendPassenger, Setup}
import poo.Passenger

import scala.collection.mutable.ListBuffer
class Main {
  
}


/**
 * Created by Group 2 on 11/20/2014.
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
     * - Doc Check Actor
     */

    val lineQueues = new ListBuffer[ActorRef]

    val system = ActorSystem.create("mySystem")

    val jailActor = system.actorOf(Props(classOf[Jail], system))
    val systemActor = system.actorOf(Props(classOf[System], NUM_PASSENGERS))

    /* Create the lines and each respective actor */
    for (a <- 1 to NUM_LINES) {
      println("Creating line: " + a)
      val securityActor = system.actorOf(Props(classOf[Security], jailActor, systemActor, a))
      val bagScannerActor = system.actorOf(Props[BagScanner])
      val bodyScannerActor = system.actorOf(Props[BodyScanner])
      val queueActor = system.actorOf(Props[LineQueue])
      val setupMsg = Setup(queueActor, securityActor, bodyScannerActor, bagScannerActor, a)

      lineQueues += queueActor

      bagScannerActor ! setupMsg
      bodyScannerActor ! setupMsg
      queueActor ! setupMsg
    }

    val documentCheckActor = system.actorOf(Props(classOf[DocumentCheck], systemActor, lineQueues))
    systemActor ! documentCheckActor

    /* Create the passengers and send them over to document check actor */
    for (passNum <- 1 to NUM_PASSENGERS) {
      // Create passenger and send it to document Check actor
      val passenger = new Passenger(passNum)
      documentCheckActor ! SendPassenger(passenger, true, false)
    }

  }
}