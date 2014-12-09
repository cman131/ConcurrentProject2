/**
 * Created by Conor on 12/3/2014.
 * The Body scanner in the airport
 */

package actors

import akka.actor.{ActorRef, Actor}
import messages.Setup
import messages.SendPassenger
import messages.Notify

class BodyScanner extends Actor {

	var queue: ActorRef
	var security: ActorRef

	def receive = {
		case msg: Setup => // receive setup msg
			queue = msg.getQueue()
			security = msg.getSecurity()
		case msg: SendPassenger => // receive a new passenger
			val result: Boolean = (new util.Random).nextInt(5)!=2
			security ! new SendPassenger(msg.passenger, result)
			queue ! new Notify(true)
	}
}
