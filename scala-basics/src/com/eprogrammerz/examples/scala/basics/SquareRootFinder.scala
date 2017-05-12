package com.eprogrammerz.examples.scala.basics

object SquareRootFinder {
  def sqrtIter(guess: Double, x: Double): Double =
    if (isGoodEnough(guess, x)) guess
    else sqrtIter(improve(guess, x), x)

  def isGoodEnough(guess: Double, x: Double) = math.abs(square(guess) - x) < 0.001

  def improve(guess: Double, x: Double) = (guess + x / guess) / 2

  def square(x: Double) = x * x

  def sqrt(x: Double) = sqrtIter(1.0, x)

  def main(args: Array[String]): Unit = {
    println(sqrt(2))
    println(sqrt(4))
  }
}