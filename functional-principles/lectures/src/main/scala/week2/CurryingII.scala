package week2

import scala.annotation.tailrec

/**
  * Sum and product function can be combined into one
  */
object CurryingII {

  def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
    @tailrec
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc else loop(a + 1, combine(acc, f(a)))
    }

    loop(a, zero)
  }

  def sum(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x, y) => x + y, 0)(a, b)

  def product(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x, y) => x * y, 1)(a, b)

  def main(args: Array[String]): Unit = {
    println(sum(x => x)(2, 3))
    println(product(x => x)(2, 3))
  }
}
