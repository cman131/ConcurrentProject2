/**
 * Created by Conor on 12/3/2014.
 * The Body scanner in the airport
 */

import akka.actor.Actor
sealed abstract class Message
case object Setup extends Message
case object SendPassenger extends Message

class BodyScanner extends Actor[Message] {
	var queue: Queue
	var security: Security

	def receive = {
		case msg: Setup => // receive setup msg
			queue = msg.queueRef
			security = msg.secRef
		case msg: SendPassenger => // receive a new passenger
			result = (new scala.util.Random).nextInt(5)!=2
			security.tell(new SendPassenger(msg.passenger, result), getSelf())
			queue.tell(new Notify(true), getSelf())
	}
}
