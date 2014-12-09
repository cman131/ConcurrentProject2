package poo

/**
 * poo.Passenger.scala
 *
 * Simple class to represent a passenger who owns one bag.
 *
 * @author Geoff Berl
 */

class Passenger(id: Integer) {

  // Some fields maybe??
  var hasBag: Boolean = true

  // Some functions or getters???
  def getHasBag: Boolean = {
    this.hasBag
  }

  def setHasBag(b: Boolean) {
    this.hasBag = b
  }

  def getId(): Integer = {
    return id
  }

}
