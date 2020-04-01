package com.eprogrammerz.examples.scala.obj


object Sorting {
  def insert(x: Int, xs: List[Int]): List[Int] = xs match {
    case List() => List(x)
    case y::ys => if (x <= y) x::xs else y::insert(x, ys)
  }

  /**
    * Time O(n^2)
    *
    * @param lst
    * @return sorted list
    */
  def sort(lst: List[Int]): List[Int] = lst match {
    case List() => Nil
    case x::xs => insert(x, sort(xs))
  }

  def main(args: Array[String]): Unit = {
    val nums = List(3,7,1,2,5,4)
    println(sort(nums))
  }
}
