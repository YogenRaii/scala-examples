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
  }

  class Cons[T](val head: T, val tail: List[T]) extends List[T] {
    override def isEmpty: Boolean = false
  }
}

object ListsTest {
  import Lists._
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
    println(nth(3, xs))
  }
}
