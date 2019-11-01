package part1recap

import scala.util.Try

/**
  * Created by soner.guzeloglu onn 30.10.2019
  */
object GeneralRecap extends App {
  val aCondition: Boolean = false

  var aVar = 42
  aVar += 1 // aVariable = 43

  val aConditionedVal = if (aCondition) 42 else 65

  // code block
  val aCodeBlock = {
    if (aCondition) 74
    56
  }

  // types
  // Unit
  val theUnit = println("Hello , Scala")


  def aFunction(x: Int) = x + 1

  // Recursion - TAIL RECURSION
  def factorial(n: Int, acc: Int): Int =
    if (n <= 0) acc
    else factorial(n - 1, acc * n)

  // OOP
  class Animal

  class Dog extends Animal

  val aDog: Animal = new Dog

  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch!")
  }

  // Method notations
  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog

  // anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("Roar")
  }

  aCarnivore eat aDog

  // generics
  abstract class MyList[+A]

  // companion objects
  object MyList

  // case classes
  case class Person(name: String, age: Int) // a LOT in this course!

  // Exceptions
  val aPotentialFailure = try {
    throw new RuntimeException("i am innocent!")
  } catch {
    case e: Exception => "I caught an exception!"
  } finally {
    // side effects
    println("Some logs")
  }


  // Functional Programming

  val incrementer = new Function[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  val incremented = incrementer(42) // 43
  // incrementer.apply(42)

  val anonymousIncrementer = (x: Int) => x + 1
  // Int => Int === Function1[Int, Int]

  // FP is all about working with functions as first-class
  List(1, 2, 3).map(incrementer)
  // map = HOF

  // for comprehensions
  val pairs = for {
    num <- List(1, 2, 3, 4)
    char <- List('a', 'b')
  } yield num + "-" + char

  // List(1,2,3,4).flatMap(num => List('a','b').map(char => num + "-" + char))

  // Seq, Array, List, Vector, Map, Tuples, Sets

  // Collections
  // Option and Try

  val anOption = Some(2)
  val aTry = Try {
    throw new RuntimeException
  }

  // Pattern Matching
  val unknown = 2
  val order = unknown match{
    case 1 => "first"
    case 2 => "second"
    case _ => "unknown"
  }
  val bob = Person("bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hi. my name is $n"
    case _ => "I dont know my name"
  }

  // All the patterns

}
