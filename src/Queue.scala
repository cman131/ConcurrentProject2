/**
 * Created by Conor on 12/3/2014.
 * The queue in the airport
 */

import akka.actor.Actor
import scala.collection.immutable.Queue

sealed abstract class Message
case object Setup extends Message
case object SendPassenger extends Message
case object Notify extends Message

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
				bag.tell(new SendPassenger(bags.dequeue()), getSelf())
				isBagFree = false
			}
			if(isBodyFree){
				body.tell(new SendPassenger(bodies.dequeue()), getSelf())
				isBodyFree = false
			}

		case msg: Notify => // a scanner has become free
		{
			case b: BodyScanner =>
				if(bodies.size()>0){
					body.tell(new SendPassenger(bodies.dequeue()), getSelf())
				}
				else{
					isBodyFree = true;
				}
			case b: BagScanner =>
				if(bags.size()>0){
					bag.tell(new SendPassenger(bags.dequeue()), getSelf())
				}
				else{
					isBagFree = true;
				}
		}
	}
}
