package actors


/**
 * actors.DocumentCheck.scala
 * 
 * This class is like a queue in which it is sent a list of passengers.  As
 * 'Line's request passengers, actors.DocumentCheck will distribute them until there
 * are no more.  In addition, Document check will dismiss passengers at a 
 * probability of 15% to simulate documentation related issues with reference
 * to attempting to enter the airport entry line(s).
 * 
 * @author Geoff Berl
 */;

import akka.actor.{ Actor, ActorRef }
import messages._
import poo.Passenger

class DocumentCheck extends Actor {
  
//  var queues : List[actors.Queue]
  var passengers : scala.collection.mutable.Stack[Passenger]
  var system : ActorRef
  var queues : List[LineQueue]
  var nextLineQ : Int = 0
  
  /**
   * Abstract Constructor to receive reference to the system so that 
   * the class object can send docCheck failures to the system.
   */
  def this(system : ActorRef, queues : List[LineQueue]) {
    this()
    this.system = system
    this.queues = queues
    
  }
  
  
  // Receive messages
  def receive = {
    // If I receive a passenger, push them onto the stack
    case SendPassenger(psgr, true) => 
      passengers.push(psgr)
      sendPassenger()
    // If I receive a poison pill, bite cyanide tablet in mouth
    case PoisonPill(die) => context.stop(self)
    // messages.Notify console when I don't understand a message
    case _ => println("DocCheck: I got a msg I don't understand")
  }
  
  def sendPassenger() {
    // If no passengers... you lose, you get NOTHING!
    if (passengers.isEmpty) {
      return
    }
    
    // Create random and pop our passenger
    var rand = new scala.util.Random
    val psgr : Passenger = passengers.pop()
    
    // If there are passengers, implement a 20% chance of failing.
    if (rand.nextFloat() <= 0.20f) {
      system ! Notify(true)
    } else { // or send one... maybe, if we got this far
      queues(nextLineQ).self ! psgr
      incLineQueue()
    }
    
  }
  
  def incLineQueue() {
    if (nextLineQ == queues.size-1) {
      // Reset
      nextLineQ = 0
    } else {
      // Next
      nextLineQ += 1  
    }
  }
  
}