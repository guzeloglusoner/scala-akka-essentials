package part1recap

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by soner.guzeloglu onn 30.10.2019
  */
object MultiThreadingRecap  extends App {

  // creating a thread
  val aThread = new Thread(() => println("I am running in parallel"))

  aThread.start()
  aThread.join()

  val threadHello = new Thread(() => (1 to 1000).foreach(_ => println("hello")))
  val threadGoodbye = new Thread(() => (1 to 1000).foreach(_ => println("goodbye")))
  threadHello.start()
  threadGoodbye.start()

  // different runs produce different results!

  class BankAccount(@volatile private var amount: Int){
    override def toString: String = "" + amount

    def withDraw(money: Int): Unit = this.amount -= money

    def safeWithDraw(money: Int) = this.synchronized{
      this.amount -= money
    }
  }

  /*
    BA(10000)

    t1 -> withdraw 1000
    t2 -> withdraw 2000

    NOT ATOMIC
   */

  // inter-thread communication on JVM
  // wait - notify mechanism

  // Scala Futures
  import scala.concurrent.ExecutionContext.Implicits.global
  val future = Future{
      // long computation - on a different thread
    42
  }

  // callbacks
  future.onComplete{
    case Failure(_) => println("Sth happened")
    case Success(42) => println("I found")
  }

  val aProcessedFuture = future.map(_ + 1) // Future with 43
  val aFlatFuture = future.flatMap{
    value =>
      Future(value + 2)
  } // Future with 44

  val filteredFuture = future.filter(_ % 2 == 0) // nosuchelement Exception


  val aNonseFuture = for{
    meaningOfLife <- future
    filteredMeaning <- filteredFuture
  } yield meaningOfLife + filteredMeaning

  // andThen, recover,recoverWith

  // Promises
}
