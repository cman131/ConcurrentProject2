package actors

/**
 * Created by Conor on 12/3/2014.
 * The queue in the airport
 */

import akka.actor.{ActorRef, Actor}

import messages.Setup
import messages.SendPassenger
import messages.Notify
import poo.Passenger

import collection.mutable.Queue

class LineQueue extends Actor {
	var body: ActorRef
	var bag: ActorRef
	var isBagFree = true
	var isBodyFree = true

	val bodies = new Queue[Passenger]
	val bags = new Queue[Passenger]

	def receive = {
		case msg: Setup => // receive setup msg
			body = msg.getBody()
			bag = msg.getBag()
		case msg: SendPassenger => // receive a new passenger
			bodies += msg.passenger //+: bodies
			bags += msg.passenger //+: bags
			// Now update the scanners if they are free
			if(isBagFree){
				bag ! new SendPassenger(bags.dequeue())
				isBagFree = false
			}
			if(isBodyFree){
				body ! new SendPassenger(bodies.dequeue())
				isBodyFree = false
			}

		case msg: Notify => // a scanner has become free
			case b: BodyScanner =>
				if(bodies.size>0){
					body ! new SendPassenger(bodies.dequeue())
				}
				else{
					isBodyFree = true;
				}
			case b: BagScanner =>
				if(bags.size>0){
					bag ! new SendPassenger(bags.dequeue())
				}
				else{
					isBagFree = true;
				}
		
	}
}
