package com.eprogrammerz.examples.scala.basics

/**
  * Example to see function as first-class citizens
  */
object HigherOrderFunction {
  /*
  // sum of num between a and b
  def idSum(a: Int, b: Int): Int = {
    if (a > b) 0 else a + idSum(a + 1, b)
  }

  // sum of cube between a and b
  def cubeSum(a: Int, b: Int): Int = {
    if (a > b) 0 else cube(a) + cubeSum(a + 1, b)
  }

  // sum of fact between a and b
  def factSum(a: Int, b: Int): Int = {
    if (a > b) 0 else fact(a) + factSum(a + 1, b)
  }*/


  // better version with higher order function
  def sum(f: Int => Int, a : Int, b: Int): Int = if (a > b) 0 else f(a) + sum(f, a + 1, b)

  /*
  def idSum(a: Int, b: Int): Int = sum(id, a, b)

  def cubeSum(a: Int, b: Int): Int = sum(cube, a, b)

  def factSum(a: Int, b: Int): Int = sum(fact, a, b)

  def fact(i: Int): Int = if (i == 0) i else i * fact(i - 1)

  def id(i: Int): Int = i

  def cube(i: Int): Int = i * i * i*/

  //anonymous function use
  def idSum(a: Int, b: Int): Int = sum(x => x, a, b)

  def cubeSum(a: Int, b: Int): Int = sum(x => x * x * x, a, b)

  def factSum(a: Int, b: Int): Int = sum(fact, a, b)

  def fact(i: Int): Int = if (i == 0) 1 else i * fact(i - 1)



  def main(args: Array[String]): Unit = {
    println(idSum(2,3))
    println(cubeSum(2,3))
    println(factSum(2,3))
  }
}
