import scala.annotation.tailrec

object lecture {

  def sum(f: Int => Int, a: Int, b: Int): Int = {
    @tailrec
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
    }
    loop(a, 0)
  }

  def sumOfCubes(a: Int, b: Int): Int = sum(x => x * x * x, a, b)

  sumOfCubes(2, 4)


  def fact(n: Int): Int = {
    if (n <= 0) 1
    else n * fact(n - 1)
  }

  def sumOfFactorial(a: Int, b: Int): Int = sum(fact, a, b)

  sumOfFactorial(2, 4)
}