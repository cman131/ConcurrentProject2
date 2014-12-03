/**
 * Created by Conor on 12/3/2014.
 */

sealed abstract class Message
case object Setup extends Message
case object SendPassenger extends Message
case object Notify extends Message

class Queue extends Actor[Message] {
	def receive = {
		case Setup => () // OK
	}
}
