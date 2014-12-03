/**
 * Created by Conor on 12/3/2014.
 * The Body scanner in the airport
 */

import akka.actor.Actor
sealed abstract class Message
case object Setup extends Message
case object SendPassenger extends Message

class BodyScanner extends Actor[Message] {
	def receive = {
		case Setup => () // receive setup msg
		case SendPassenger => () // receive a new passenger
	}
}
