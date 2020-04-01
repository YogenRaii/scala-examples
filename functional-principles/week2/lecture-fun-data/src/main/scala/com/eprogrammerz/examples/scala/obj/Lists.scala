package com.eprogrammerz.examples.scala.obj

import scala.annotation.tailrec

object Lists {
  trait List[T] {
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
  }

  class Nil[T] extends List[T] {
    override def isEmpty: Boolean = true

    override def head: Nothing = throw new NoSuchElementException

    override def tail: Nothing = throw new NoSuchElementException

    override def toString: String = "."
  }

  class Cons[T](val head: T, val tail: List[T]) extends List[T] {
    override def isEmpty: Boolean = false

    override def toString: String = "{" + head + tail + "}"
  }
}


object ListsTest {
  import Lists._

  /**
    * functions are objects
    *
    * (t: T) => y
    *
    * from
    *
    * class Function1 {
    *   def apply(t: T): T
    * }
    */
  object List {
    import Lists._
    def apply[T](x1: T, x2: T): List[T] = new Cons[T](x1, new Cons[T](x2, new Nil[T]))

    def apply[T](x1: T): List[T] = new Cons[T](x1, new Nil[T])

    def apply[T](): List[T] = new Nil
  }

  // find nth element on given List
  @tailrec def nth(n: Int, xs: List[Int]): Int = {
    if (n == 0) xs.head
    else nth(n - 1, xs.tail)
  }

  def main(args: Array[String]): Unit = {
    val xs: List[Int] = new Cons[Int](1, new Cons[Int](2, new Cons[Int](3, new Nil[Int])))

    println(nth(0, xs))
    println(nth(1, xs))
    println(nth(2, xs))
//    println(nth(3, xs)) // Exception in thread "main" java.util.NoSuchElementException

    val l1: List[Int] = List(1, 2)
    val l2: List[Int] = List(1)
    val l3: List[Int] = List()

    println(l1)
    println(l2)
    println(l3)
  }
}
