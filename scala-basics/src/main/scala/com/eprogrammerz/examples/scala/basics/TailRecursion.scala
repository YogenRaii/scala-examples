package com.eprogrammerz.examples.scala.basics

import scala.annotation.tailrec

object TailRecursion {

  def fact(n: Int): Int = {

    @tailrec
    def loop(acc: Int, n: Int): Int = {
      if (n == 0) acc
      else loop(acc * n, n - 1)
    }

    loop(1, n)
  }

  def main(args: Array[String]): Unit = {
    println(fact(3))
    println(fact(4))
    println(fact(5))
  }

}
