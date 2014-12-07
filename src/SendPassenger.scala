/**
 * Created by Conor on 11/21/2014.
 */
class SendPassenger(passenger: ActorRef, pass: Boolean) extends Message{
  def this(passenger: ActorRef) = this(passenger,true)

}