package week2

import scala.annotation.tailrec

/**
  * Currying -> transforming a function
  */
object CurryingI {
  def product(f: Int => Int)(a: Int, b: Int): Int = {
    @tailrec
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc else loop(a + 1, f(a) * acc)
    }

    loop(a, 1)
  }

  def fact(n: Int): Int = product(x => x) (1, n)

  def main(args: Array[String]): Unit = {
    println(product(x => x)(2, 3))
    println(product(x => x * x) (3, 4))

    println(fact(2))
    println(fact(3))
    println(fact(4))
    println(fact(5))
  }
}
