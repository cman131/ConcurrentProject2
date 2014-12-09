package messages

/**
 * Created by Conor on 11/21/2014.
 */

import akka.actor.ActorRef

case class Setup(queueRef : ActorRef, securityRef : ActorRef, bodyRef: ActorRef, bagRef: ActorRef, line: Integer) {
  def getQueue(): ActorRef = {
    return queueRef
  }

  def getSecurity(): ActorRef = {
    return securityRef
  }

  def getBody(): ActorRef = {
    return bodyRef
  }

  def getBag(): ActorRef = {
    return bagRef
  }

  def getLine(): Integer = {
    return line
  }
}