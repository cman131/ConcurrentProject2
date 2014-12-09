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
	var line: Integer

	def receive = {
		case msg: Setup => // receive setup msg
			queue = msg.getQueue()
			security = msg.getSecurity()
			line = msg.getLine()
			println("Body Scanner #"+line+" is set up.")
		case msg: SendPassenger => // receive a new passenger
			println("Body Scanner #"+line+" has received a new passenger.")
			val result: Boolean = (new util.Random).nextInt(5)!=2
			println("Body Scanner #"+line+" sends passenger #"+msg.passenger.getId()+" to security.")
			security ! new SendPassenger(msg.passenger, result)
			println("Body Scanner #"+line+" notifies queue #"+line+" that it's empty.")
			queue ! new Notify(true)
	}
}
