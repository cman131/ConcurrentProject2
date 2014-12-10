/**
 * Created by Conor on 12/3/2014.
 * The Body scanner in the airport
 */

package actors

import akka.actor.{ActorRef, Actor}
import messages.{PoisonPill, Setup, SendPassenger, Notify}

class BodyScanner extends Actor {

	var queue: ActorRef = null
	var security: ActorRef = null
	var line: Integer = null

	def receive = {
		// poison siblings and kill self on poison pill
		case msg: PoisonPill =>
			println("Body Scanner #"+line+": PoisonPill -> Shutting down.")
			println("Body Scanner #"+line+": is now poisoning Security #"+line+".")
			security ! new PoisonPill(true)
			context.stop(self)
		// set references and line state
		case msg: Setup => // receive setup msg
			queue = msg.getQueue()
			security = msg.getSecurity()
			line = msg.getLine()
			println("Body Scanner #"+line+": is set up.")
		// Receive and process a passenger
		case msg: SendPassenger => // receive a new passenger
			println("Body Scanner #"+line+": has received passenger #"+msg.passenger.getId()+".")
			val result: Boolean = (new util.Random).nextInt(5)!=2
			println("Body Scanner #"+line+": sends passenger #"+msg.passenger.getId()+" to security.")

			// notify the security and queue
			security ! new SendPassenger(msg.passenger, result, true)
			println("Body Scanner #"+line+": notifies queue #"+line+" that it's empty.")
			queue ! new Notify(true)
	}
}
