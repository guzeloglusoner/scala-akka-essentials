package part1recap

import scala.concurrent.Future

/**
  * Created by soner.guzeloglu onn 30.10.2019
  */
object ThreadModelLimitations extends App {

  /*
  Daniel's rants
   */

  /**
    * DR #1: OOP encapsulation is only valid in the SINGLE THREAD MODEL
    */

  class BankAccount(private var amount: Int) {
    override def toString: String = "" + amount

    def withDraw(money: Int): Unit = this.synchronized {
      this.amount -= money
    }

    def deposit(money: Int): Unit = this.synchronized {
      this.amount += money
    }

    def getAmount = amount
  }


  // OOP encapsulation is dead here

  // sync! Locks to the rescue

  // deadlocks, livelocks

  /**
    * DR #2: delegating to sth to a thread is a PAIN
    */
  // executor service
  // you have a running thread and you want to pass a runnable to that thread

  var task: Runnable = null

  val runningThread: Thread = new Thread(() => {
    while (true) {
      while (task == null) {
        runningThread.synchronized {
          println("[background] waiting for a task...")
          runningThread.wait()
        }
      }

      task.synchronized {
        println("[background] I have a task!")
        task.run()
        task = null
      }
    }
  })

  def delegateToBackgroundThread(r: Runnable) = {
    if (task == null) task = r

    runningThread.synchronized {
      runningThread.notify()
    }
  }

  runningThread.start()
  Thread.sleep(1000)
  delegateToBackgroundThread(() => println(42))
  Thread.sleep(1000)
  delegateToBackgroundThread(() => println("This should run in the background"))

  /**
    * DR #3: tracing and dealing with errors in a multithreaded env is a PITN.
    */

  // 1M number in between 10 threads
  import scala.concurrent.ExecutionContext.Implicits.global

  val futures = (0 to 9).map(i => 100000 * i until 100000 * (i + 1)) // 0 - 9999, 100000 - 199999, etc
    .map(range => Future {
      if (range.contains(546735)) throw new RuntimeException("invalid number")
      range.sum
  })

  val sumFuture = Future.reduceLeft(futures)(_ + _) // Future with the sum of all the numbers
  sumFuture.onComplete(println)

}
