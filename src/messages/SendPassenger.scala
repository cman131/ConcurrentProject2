package messages

import poo.Passenger

/**
 * Created by Conor on 12/8/2014.
 */
class SendPassenger(passenger : Passenger, result : Boolean) {
  def this(passenger: Passenger){ this(passenger,true)}

  def getPassenger(): Passenger = {
    return passenger
  }

  def getPassValue(): Boolean = {
    return result
  }
}
