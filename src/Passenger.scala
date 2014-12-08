/**
 * Passenger.scala
 * 
 * Simple class to represent a passenger who owns one bag.
 * 
 * @author Geoff Berl
 */

import akka.actor.Actor

class Passenger {
  
  // Some fields maybe??
  var hasBag : Boolean = true;
  
  // Some functions or getters???
  def getHasBag() : Boolean = {
    return this.hasBag;
  }
  
  def setHasBag(b : Boolean) {
    this.hasBag = b;
  }
  
}
