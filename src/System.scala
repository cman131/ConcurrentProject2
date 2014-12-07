/**
 * System.scala
 * 
 * System is in charge of making Passenger objects and sending their references
 * to the DocumentCheck object for the airport application.
 */
import akka.actor.Actor;
import akka.actor.ActorRef;
import scala.collection.mutable.ListBuffer

class System extends Actor {

  //var docCheck : ActorRef;
  var passengers = new ListBuffer[Passenger]();
  
  /**
   * Create a system with some number of airport check lines
   */
  def this(numPassengers : Int) = {
    this();
    
    // Create passengers
    for(i <- 1 to numPassengers) {
      val passenger = new Passenger();
      passengers += passenger;
    }
    
  }
  
  def receive = {
    case "Main" => print("YO, system just received a message from Main SuN.")
    case _      => 
  }



}