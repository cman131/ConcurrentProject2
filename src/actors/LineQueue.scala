package actors

/**
 * Created by Conor on 12/3/2014.
 * The queue in the airport
 */

import akka.actor.{ActorRef, Actor}

import messages.{PoisonPill, Setup, SendPassenger, Notify}
import poo.Passenger

import collection.mutable.Queue

class LineQueue extends Actor {
	// Define references to known actors
	var body: ActorRef =  null
	var bag: ActorRef = null
	
	//Define state of line and scanners
	var line: Integer = null
	var isBagFree = true
	var isBodyFree = true

	// Tracks bags and people who haven't been scanned yet
	val bodies = new Queue[Passenger]
	val bags = new Queue[Passenger]

	def receive = {
		// Kill self and siblings on poison pill message
		case msg: PoisonPill =>
			println("Queue #"+line+": PoisonPill -> Shutting down.")
			println("Queue #"+line+": poisoning Bag Scanner #"+line+".")
			bag ! new PoisonPill(true)
			println("Queue #"+line+": poisoning Body Scanner #"+line+".")
			body ! new PoisonPill(true)
			context.stop(self)
		// set up reference and line state on setup message
		case msg: Setup => // receive setup msg
			body = msg.getBody()
			bag = msg.getBag()
			line = msg.getLine()
			println("Queue #"+line+": is set up.")
		case msg: SendPassenger => // receive a new passenger
			println("Queue #"+line+": has received passenger #"+msg.passenger.getId()+".")
			
			// log the new passenger and bag
			bodies += msg.passenger //+: bodies
			bags += msg.passenger //+: bags

			// Now update the scanners if they are free
			if(isBagFree){
				println("Queue #"+line+": sends passenger #"+msg.passenger.getId()+" to Bag Scanner #"+line+".")
				bag ! new SendPassenger(bags.dequeue())
				isBagFree = false
			}
			if(isBodyFree){
				println("Queue #"+line+": sends passenger #"+msg.passenger.getId()+" to Body Scanner #"+line+".")
				body ! new SendPassenger(bodies.dequeue())
				isBodyFree = false
			}
		// Log that the scanner is free and fill it if able
		case msg: Notify => // a scanner has become free
			case b: BodyScanner =>
				println("Queue #"+line+": has been notified that Body Scanner #"+line+"is free.")
				if(bodies.size>0){
					val pass: Passenger = bodies.dequeue()
					println("Queue #"+line+": sends passenger #"+pass+" to Body Scanner #"+line+".")
					body ! new SendPassenger(pass)
				}
				else{
					isBodyFree = true
				}
			case b: BagScanner =>
				println("Queue #"+line+": has been notified that Bag Scanner #"+line+"is free.")
				if(bags.size>0){
					val pass: Passenger = bags.dequeue()
					println("Queue #"+line+": sends passenger #"+pass+" to Bag Scanner #"+line+".")
					bag ! new SendPassenger(pass)
				}
				else{
					isBagFree = true
				}
		
	}
}
