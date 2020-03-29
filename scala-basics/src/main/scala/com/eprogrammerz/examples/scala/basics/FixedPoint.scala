package com.eprogrammerz.examples.scala.basics

import scala.annotation.tailrec

/**
  * example of use of fixed point to find square root
  */
object FixedPoint {
  def fixedPoint(f: Double => Double) (firstGuess: Int): Double = {
    val tolerance = 0.00001

    @tailrec
    def loop(guess: Double): Double = {
      val next = f(guess)
      if (goodEnough(guess, next)) guess
      else loop(next)
    }

    def goodEnough(x: Double, y: Double): Boolean = math.abs(x - y) < tolerance

    loop(firstGuess)
  }

  def avgDamp(f: Double => Double)(x: Double): Double = (x + f(x)) / 2

  def sqrt(x: Double): Double = {
    fixedPoint(avgDamp(y => x / y))(1)
    /*@tailrec
    def loop(guess: Double): Double = {
      if (goodEnough(guess)) guess
      else loop((guess + n / guess) / 2)
    }

    def goodEnough(guess: Double): Boolean = math.abs(square(guess) - n) / n < 0.00001

    def square(x: Double): Double = x * x

    loop(1)*/
  }
  def main(args: Array[String]): Unit = {
    println(fixedPoint(x => 1 + 2 / x)(1))
    println(sqrt(2))
  }
}
