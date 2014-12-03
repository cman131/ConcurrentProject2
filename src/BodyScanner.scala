/**
 * Created by Conor on 12/3/2014.
 */

sealed abstract class Message
case object Setup extends Message
case object SendPassenger extends Message
case object Notify extends Message

class Queue extends Actor[Message] {
	def receive = {
		case Setup => () // receive setup msg
		case SendPassenger => () // receive a new passenger
		case Notify => () // a scanner has become free
	}
}
