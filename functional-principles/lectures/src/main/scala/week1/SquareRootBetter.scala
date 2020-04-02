package week1

/**
  * Netwon's method to find Square root
  */
object SquareRootBetter {

  // hide methods inside blocks
  def sqrt(x: Double): Double = {
    def sqrtItr(guess: Double): Double =
      if (isGoodEnough(guess)) guess
      else sqrtItr(improve(guess))

    // to make sure the diff falls within the epsilon, need to divide the diff with x
    def isGoodEnough(guess: Double): Boolean = math.abs(square(guess) - x) / x < 0.001

    def improve(guess: Double): Double = (guess + x / guess) / 2

    sqrtItr(1.0)
  }

  def square(x: Double): Double = x * x

  def main(args: Array[String]): Unit = {
    println(sqrt(2))
    println(sqrt(4))
    println(sqrt(1e-6))
    println(sqrt(1e60))
  }
}
