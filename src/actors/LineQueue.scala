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
	var body: ActorRef =  null
	var bag: ActorRef = null
	var line: Integer = null
	var isBagFree = true
	var isBodyFree = true

	val bodies = new Queue[Passenger]
	val bags = new Queue[Passenger]

	def receive = {
		case msg: PoisonPill =>
			println("Queue #"+line+" has been poisoned! Shutting down.")
			println("Queue #"+line+" is now poisoning Bag Scanner #"+line+".")
			bag ! new PoisonPill(true)
			println("Queue #"+line+" is now poisoning Body Scanner #"+line+".")
			body ! new PoisonPill(true)
			context.stop(self)
		case msg: Setup => // receive setup msg
			body = msg.getBody()
			bag = msg.getBag()
			line = msg.getLine()
			println("Queue #"+line+" is set up.")
		case msg: SendPassenger => // receive a new passenger
			println("Queue #"+line+" has received a new passenger.")
			bodies += msg.passenger //+: bodies
			bags += msg.passenger //+: bags
			// Now update the scanners if they are free
			if(isBagFree){
				println("Queue #"+line+" sends passenger #"+msg.passenger.getId()+" to Bag Scanner #"+line+".")
				bag ! new SendPassenger(bags.dequeue())
				isBagFree = false
			}
			if(isBodyFree){
				println("Queue #"+line+" sends passenger #"+msg.passenger.getId()+" to Body Scanner #"+line+".")
				body ! new SendPassenger(bodies.dequeue())
				isBodyFree = false
			}

		case msg: Notify => // a scanner has become free
			case b: BodyScanner =>
				println("Queue #"+line+" has been notified that Body Scanner #"+line+"is free.")
				if(bodies.size>0){
					val pass: Passenger = bodies.dequeue()
					println("Queue #"+line+" sends passenger #"+pass+" to Body Scanner #"+line+".")
					body ! new SendPassenger(pass)
				}
				else{
					isBodyFree = true
				}
			case b: BagScanner =>
				println("Queue #"+line+" has been notified that Bag Scanner #"+line+"is free.")
				if(bags.size>0){
					val pass: Passenger = bags.dequeue()
					println("Queue #"+line+" sends passenger #"+pass+" to Bag Scanner #"+line+".")
					bag ! new SendPassenger(pass)
				}
				else{
					isBagFree = true
				}
		
	}
}
