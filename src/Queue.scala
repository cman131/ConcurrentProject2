/**
 * Created by Conor on 12/3/2014.
 * The queue in the airport
 */

import akka.actor.Actor
import scala.collection.immutable.Queue

sealed abstract class Message

class Queue extends Actor[Message] {
	var body: BodyScanner
	var bag: BagScanner
	var isBagFree = true
	var isBodyFree = true

	val bodies = Queue.empty[Passenger]
	val bags = Queue.empty[Passenger]

	def receive = {
		case msg: Setup => // receive setup msg
			body = msg.bodyRef
			bag = msg.bagRef
		case msg: SendPassenger => // receive a new passenger
			bodies = msg.passenger +: bodies
			bags = msg.passenger +: bags
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
		{
			case b: BodyScanner =>
				if(bodies.size()>0){
					body ! new SendPassenger(bodies.dequeue())
				}
				else{
					isBodyFree = true;
				}
			case b: BagScanner =>
				if(bags.size()>0){
					bag ! new SendPassenger(bags.dequeue())
				}
				else{
					isBagFree = true;
				}
		}
	}
}
