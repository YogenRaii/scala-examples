package com.eprogrammerz.examples.scala.basics

object SortingExample {
  def main(args: Array[String]): Unit = {
    println(quickSort(Array(1, 5, 2, 9, 4, 7)).toList)
  }

  def quickSort(xs: Array[Int]): Array[Int] = {
    if (xs.length <= 1) xs
    else {
      val pivot = xs(xs.length / 2)
      Array.concat(quickSort(xs filter (pivot >)),
        xs filter (pivot ==),
        quickSort(xs filter (pivot <)))
    }
  }
}