package actors;

class Security extends Actor {

  var _line : Int
  var _jail : ActorRef = null
  var _system : ActorRef = null
  var _passengers : Map[Passenger, Int] = Map()
  var _doClose : Boolean = false;
  
  
  // Configured with the line it is in and a jailRef and systemRef
  def this(_jail : ActorRef, _system : ActorRef) = {
    this();
    this._jail = _jail;
    this._system = _system;
  }
  
  /** Setters */
  def line_= (line : Int):Unit = _line = line 
  
  /** Getters */
  def line = _line
  
  /** Messages... */
  def receive = {
    // If receiving a poo.Passenger, check if they've been received twice
    case SendPassenger(passenger, true) => checkPerson(sender, passenger)
    
    // If receiving message from system, quit
    case PoisonPill(die) => tryQuit()
    
    // Handle all messages...
    case _ => print("actors.Security: Unknown message received")
  }
  
  /** Helper methods */
  
  def checkPerson(sender : ActorRef, psgr : Passenger) {
    
    // Check if they're in the map, set value to 2 if they are
    
    // If not in map, add to the map and set value to 1
    _passengers += psgr -> 1
    
    // If we are supposed to close, try it again
    if (_doClose) {
      tryQuit()
    }
  }
  
  /**
   * actors.Security can only quit if all people and bags have been received
   */
  def tryQuit() {
    _doClose = true;
    _passengers.foreach {
      key_val => if (key_val._2 != 2) { return }
    }
    // If we made it through, close up shop
    context.stop(self)
  }
}