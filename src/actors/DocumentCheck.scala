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


class DocumentCheck extends Actor {
  
//  var queues : List[actors.Queue]
  var passengers : scala.collection.mutable.Stack[Passenger]
  var system : ActorRef
  
  /**
   * Abstract Constructor to receive reference to the system so that 
   * the class object can send docCheck failures to the system.
   */
  def this(system : ActorRef) {
    this()
    this.system = system
    
//    // ugly but works for now
//    for (i <- 1 to qs.size) {
//      queues = qs(i) :: queues
//    }
    
  }
  
  
  // Receive messages
  def receive = {
    // If I receive a passenger, push them onto the stack
    case SendPassenger(psgr, true) => passengers.push(psgr)
    // If I receive a request, send a passenger to the requester
    case Notify(b) => sendPassenger(sender)
    // If I receive a poison pill, bite cyanide tablet in mouth
    case PoisonPill(die) => context.stop(self)
    // messages.Notify console when I don't understand a message
    case _ => print("DocCheck: I got a msg I don't understand")
  }
  
  def sendPassenger(rcpt : ActorRef) {
    // If no more, send poison pill msg so they stop asking
    if (passengers.isEmpty) {
      sender ! PoisonPill(true)
      return
    }
    
    // If there are passengers, implement a 85% chance of sending one.
    var rand = new scala.util.Random
    while (rand.nextFloat() > 0.15f && !passengers.isEmpty) {
      system ! passengers.pop()
    }

    // Now send one... maybe, if there aren't any left, send an empty message
    if (!passengers.isEmpty) {
      sender ! passengers.pop()
    } else {
      sender ! null
    }
    
  }
  
}