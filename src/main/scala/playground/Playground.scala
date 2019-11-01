package playground

import akka.actor.ActorSystem

/**
  * Created by soner.guzeloglu onn 24.09.2019
  */
object Playground extends App{

  val actorSystem = ActorSystem("HelloAkka")
  println(actorSystem.name)
}
