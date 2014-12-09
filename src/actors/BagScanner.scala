/**
 * Created by Conor on 12/3/2014.
 * The Bag scanner in the airport
 */
package actors

import akka.actors.Actor
import messages.Setup
import messages.SendPassenger
import messages.Notify

class BagScanner extends Actor {

	var queue: Queue
	var security: Security

	def receive = {
		case msg: Setup => // receive setup msg
			queue = msg.queueRef
			security = msg.securityRef
		case msg: SendPassenger => // receive a new passenger
			val result: Boolean = (new scala.util.Random).nextInt(5)!=2
			security ! new SendPassenger(msg.passenger, result)
			queue ! new Notify(true)
	}
}
