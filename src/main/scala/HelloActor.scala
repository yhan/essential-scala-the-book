import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class HelloActor extends Actor {
    def receive: Receive = {
        case s:String if s.startsWith("hello") => println("hello back at you")
        case _       => println("huh?")
    }
}

object Main extends App {
    val system = ActorSystem("HelloSystem")
    // default Actor constructor
    val helloActor = system.actorOf(Props[HelloActor], name = "hello-actor")

    for ( i <- 1 to 10) {
        val message = s"hello $i"
        println(s"Before sending: ${Thread.currentThread.getId} - $message")
        helloActor ! message
        println(s"After sending: ${Thread.currentThread.getId} - $message")
    }

    println("No wait and continue here")
    helloActor ! "buenos dias"
}