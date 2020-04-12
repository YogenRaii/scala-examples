package week1

import scala.util.Random

object MonteCarloPi {

  // r = 1
  // x ^ 2 + y ^ 2 = 1
  // x = 4 * a ^ 2 / (pi * a ^ 2) -> pi = 4 * count / itr

  def mcCount(itr: Int): Int = {
    var hits = 0

    val xRandom = new Random
    val yRandom = new Random

    for (i <- 0 until itr) {
      val x = xRandom.nextDouble
      val y = yRandom.nextDouble

      if (x * x + y * y < 1) hits = hits + 1
    }

    hits
  }

  def main(args: Array[String]): Unit = {
    val itr = 10000
    val pi = 4.0 * mcCount(itr) / itr

    println(pi)
  }
}
