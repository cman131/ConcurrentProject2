import akka.actor.{ActorSystem, Props}
/**
 * Created by Conor on 11/20/2014.
 * main entry point
 */
object Main {
  def main(args: Array[String]) {


    /**
     * Actors to start
     * - Jail
     * -
     */
    val system = ActorSystem("mySystem")

    val jailActor = system.actorOf(Props[Jail], "jail")


    println("Hello, world!")
  }
}