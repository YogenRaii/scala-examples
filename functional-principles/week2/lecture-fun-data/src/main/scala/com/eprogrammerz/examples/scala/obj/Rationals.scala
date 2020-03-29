package com.eprogrammerz.examples.scala.obj

/**
  * Example of class and objects
  *
  */

object Rationals {
  def main(args: Array[String]): Unit = {
    val x = new Rational(1, 3)
    val y = new Rational(5, 7)
    val z = new Rational(3, 2)

    println(x - y - z)

    println(x + x)

    println(x < y)

    println(x.max(y))

    println(x max y)

    val strange = new Rational(1, 0)

    println(strange + strange)
  }
}

class Rational(x: Int, y: Int) {

  require(y != 0, "denominator must be be non-zero")

  private def gcd(x: Int, y: Int): Int = if (y == 0) x else gcd(y, x % y)

  private val g = gcd(x, y)

  def numer: Int = x / g

  def denom: Int = y / g

  def < (that: Rational): Boolean = numer * that.denom < denom * that.numer

  def max(that: Rational): Rational = if (this < that) that else this

  def + (that: Rational): Rational =
    new Rational(
      numer * that.denom + denom * that.numer,
      denom * that.denom
    )

  def neg: Rational = new Rational(-numer, denom)

  def - (that: Rational): Rational = this + that.neg

  override def toString: String = numer + "/" + denom
}

/*
object Rationals {
  def main(args: Array[String]): Unit = {
    val x = new Rational(1, 3)
    val y = new Rational(5, 7)
    val z = new Rational(3, 2)

    println(x.sub(y).sub(z))

    println(x.add(x))

    println(x.less(y))

    println(x.max(y))

    println(x max y)

    val strange = new Rational(1, 0)

    println(strange.add(strange))
  }
}

class Rational(x: Int, y: Int) {

  require(y != 0, "denominator must be be non-zero")

  private def gcd(x: Int, y: Int): Int = if (y == 0) x else gcd(y, x % y)

  private val g = gcd(x, y)

  def numer: Int = x / g

  def denom: Int = y / g

  def less(that: Rational): Boolean = numer * that.denom < denom * that.numer

  def max(that: Rational): Rational = if (this.less(that)) that else this

  def add(that: Rational): Rational =
    new Rational(
      numer * that.denom + denom * that.numer,
      denom * that.denom
    )

  def neg: Rational = new Rational(-numer, denom)

  def sub(that: Rational): Rational = add(that.neg)

  override def toString: String = numer + "/" + denom
}
*/
