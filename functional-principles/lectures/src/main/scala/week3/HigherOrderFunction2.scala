package week3

import scala.annotation.tailrec

/**
  * Sum example with tail recursion
  */
object HigherOrderFunction2 {
  def sum(f: Int => Int, a: Int, b: Int): Int = {
    @tailrec
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
    }

    loop(a, 0)
  }

  def idSum(a: Int, b: Int): Int = sum(x => x, a, b)

  def cubeSum(a: Int, b: Int): Int = sum(x => x * x * x, a, b)

  def factSum(a: Int, b: Int): Int = sum(fact, a, b)

  def fact(n: Int): Int = {
    @tailrec
    def loop(n: Int, acc: Int): Int = {
      if (n == 0) acc
      else loop(n - 1, acc * n)
    }

    loop(n, 1)
  }

//    def fact(i: Int): Int = if (i == 0) 1 else i * fact(i - 1)

  def main(args: Array[String]): Unit = {
    println(idSum(2, 3))
    println(cubeSum(2, 3))
    println(factSum(2, 3))

    println(fact(15))
  }
}
