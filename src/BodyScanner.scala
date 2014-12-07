/**
 * Created by Conor on 12/3/2014.
 * The Body scanner in the airport
 */

import akka.actor.Actor
import akka.actor.ActorRef

class BodyScanner extends Actor[Message] {
	var queue: Queue
	var security: Security

	def receive = {
		case msg: Setup => // receive setup msg
			queue = msg.queueRef
			security = msg.secRef
		case msg: SendPassenger => // receive a new passenger
			result = (new scala.util.Random).nextInt(5)!=2
			security ! new SendPassenger(msg.passenger, result)
			queue ! new Notify(true)
	}
}
