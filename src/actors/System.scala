package actors

/**
 * actors.System.scala
 * 
 * actors.System is in charge of making poo.Passenger objects and sending their references
 * to the DocumentCheck object for the airport application.
 */

import akka.actor.{ Actor, ActorRef }
import scala.collection.mutable.ListBuffer
import poo.Passenger
import messages.{Notify, PoisonPill}

class System extends Actor {

  var psgrCnt : Int = 0
  var psgrCompleteCnt : Int = 0
  var docCheck : ActorRef = null
  
  /**
   * Create a system with some number of airport check lines
   */
  def this(numPassengers : Int) = {
    this()
    this.psgrCnt = numPassengers
  }
  
  def receive = {
    case ref : ActorRef =>
      docCheck = ref
    case Notify(bool) =>
      println("System: Notify() -> Passenger finished")
      psgrCompleteCnt += 1
      if (psgrCompleteCnt >= psgrCnt) {
        // Shut the peeps down
        docCheck ! PoisonPill(true)
      
        // shutdown myself
        println("System: Shut down others, now shutting down self")
        context.stop(self)
      }
      
      
    case _  => println("System: Received a message I don't understand")
  }



}