package week1

/**
  * Netwon's method to find Square root
  */
object SquareRootFinder {
  def sqrt(guess: Double, x: Double): Double =
    if (isGoodEnough(guess, x)) guess
    else sqrt(improve(guess, x), x)

  // to make sure the diff falls within the epsilon, need to divide the diff with x
  def isGoodEnough(guess: Double, x: Double): Boolean = math.abs(square(guess) - x) / x < 0.001

  def improve(guess: Double, x: Double): Double = (guess + x / guess) / 2

  def square(x: Double): Double = x * x

  def sqrt(x: Double): Double = sqrt(1.0, x)

  def main(args: Array[String]): Unit = {
    println(sqrt(2))
    println(sqrt(4))
    println(sqrt(1e-6))
    println(sqrt(1e60))
  }
}
