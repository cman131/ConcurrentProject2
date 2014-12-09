/**
 * Created by Conor on 12/3/2014.
 * The Body scanner in the airport
 */

package actors

import akka.actors.Actor
import messages.Setup
import messages.SendPassenger
import messages.Notify

class BodyScanner extends Actor[Message] {
	var queue: Queue
	var security: Security

	def receive = {
		case msg: Setup => // receive setup msg
			queue = msg.queueRef
			security = msg.securityRef
		case msg: SendPassenger => // receive a new passenger
			val result = (new scala.util.Random).nextInt(5)!=2
			security ! new SendPassenger(msg.passenger, result)
			queue ! new Notify(true)
	}
}
