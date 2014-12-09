package actors;

import akka.actor.{Actor, ActorRef}
import messages._
import poo.Passenger

class Security extends Actor {

  var _line : Int
  var _jail : ActorRef = null
  var _system : ActorRef = null
  var _passengers : Map[Passenger, Boolean] = Map()
  var _bags : Map[Passenger, Boolean] = Map()
  var _poisonPillCnt : Int = 0
  
  
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
    case msg : SendPassenger => checkPerson(msg.isBody, msg.passenger, msg.result)
    
    // If receiving message from system, quit
    case PoisonPill(die) => tryQuit()
    
    // Handle all messages...
    case _ => println("actors.Security: Unknown message received")
  }
  
  /** Helper methods */
  
  def checkPerson(isBody : Boolean, psgr : Passenger, result : Boolean) {
    // Add to map
    isBody match {
    case false =>
      _bags += psgr -> result
    case true =>
      _passengers += psgr -> result
    case _ => println("Security: I have no idea who this is...")
  }
    
    
    
    // if both maps have this passenger ref, clean up
    if (_bags.contains(psgr) && _passengers.contains(psgr)) {
      // If bag or passenger failed, send to jail
      if (!_bags(psgr) || !_passengers(psgr)) {
    	  _jail ! SendPassenger(psgr,result, false)
      }
      // Once both are received, notify system
      _system ! Notify(true)
    }
    
  }
  
  /**
   * actors.Security can only quit if all people and bags have been received
   */
  def tryQuit() {
    _poisonPillCnt += 1
    // If received poisonPill from both scanners, give up and die
    if (_poisonPillCnt >= 2) {
      context.stop(self)
    }
    
  }
}