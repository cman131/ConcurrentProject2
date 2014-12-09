/**
 * Created by Conor on 12/3/2014.
 * The Bag scanner in the airport
 */
package actors

import akka.actor.{ActorRef, Actor}
import messages.{PoisonPill, Setup, SendPassenger, Notify}

class BagScanner extends Actor {

	var queue: ActorRef
	var security: ActorRef
	var line: Integer

	def receive = {
		case msg: PoisonPill =>
			println("Bag Scanner #"+line+" has been poisoned! Shutting down.")
			println("Bag Scanner #"+line+" is now poisoning Security #"+line+".")
			security ! new PoisonPill(true)
			context.stop(self)
		case msg: Setup => // receive setup msg
			queue = msg.getQueue()
			security = msg.getSecurity()
			line = msg.getLine()
			println("Bag Scanner #"+line+" is set up.")
		case msg: SendPassenger => // receive a new passenger
			println("Bag Scanner #"+line+" has received a new passenger.")
			val result: Boolean = (new util.Random).nextInt(5)!=2
			println("Bag Scanner #"+line+" sends passenger #"+msg.passenger.getId()+" to security.")
			security ! new SendPassenger(msg.passenger, result)
			println("Bag Scanner #"+line+" notifies queue #"+line+" that it's empty.")
			queue ! new Notify(true)
	}
}
