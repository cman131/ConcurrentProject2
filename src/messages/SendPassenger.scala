package messages

import poo.Passenger

/**
 * Created by Conor on 12/8/2014.
 */
case class SendPassenger(passenger : Passenger, result : Boolean, isBody: Boolean) {
  
  def this(passenger : Passenger) {
    this(passenger, true, true)
  }

  def this(passenger : Passenger, pass: Boolean) {
    this(passenger, pass, true)
  }
}
