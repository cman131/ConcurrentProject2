/**
 * Created by Conor on 12/3/2014.
 * The Bag scanner in the airport
 */

import akka.actor.Actor
sealed abstract class Message
case object Setup extends Message
case object SendPassenger extends Message

class BagScanner extends Actor[Message] {
	def receive = {
		case Setup => () // receive setup msg
		case SendPassenger => () // receive a new passenger
	}
}
