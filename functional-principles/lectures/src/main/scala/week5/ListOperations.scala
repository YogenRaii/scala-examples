package week5

/**
  * Created by rajkumar on 6/24/2016.
  */
object ListOperations {
  val fruit = List("apple", "oranges", "pears")
  val nums = List(1, 2, 3)
  val diag3 = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
  val empty = List()

  //  def removeAt(n: Int, xs: List[Int]): List[Int] = (xs take n) ::: (xs drop (n + 1))
  def removeAt(n: Int, xs: List[Int]): List[Int] = xs match {
    case List() => xs
    case _ :: ys if n == 0 => ys
    case y :: ys => y :: removeAt(n - 1, ys)
  }

  def main(args: Array[String]): Unit = {
    println(removeAt(1, nums))
    println(removeAt(1, empty))
  }
}
