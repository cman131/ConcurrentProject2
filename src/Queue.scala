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
	val body
	val bag
	val isBagFree = True
	val isBodyFree = True

	val bodies = Queue.empty[Passenger]
	val bags = Queue.empty[Passenger]

	def receive = {
		case Setup(msg) => // receive setup msg
			body = msg.bodyRef
			bag = msg.bagRef
		case SendPassenger(msg) => () // receive a new passenger
			bodies = msg.passenger +: bodies
			bags = msg.passenger +: bags
			// Now update the scanners if they are free

		case Notify(msg) => // a scanner has become free
			{
			case BodyScanner =>
				//TODO
			case BagScanner =>
				//TODO
			default =>
				// I've been poisoned
			}

	}
}
