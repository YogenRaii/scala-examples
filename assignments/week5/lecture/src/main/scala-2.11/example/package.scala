/**
  * Created by rajkumar on 6/24/2016.
  */
object example {
  val fruit = List("apple","oranges","pears")
  val nums = List(1,2,3)
  val diag3 = List(List(1,0,0),List(0,1,0),List(0,0,1))
  val empty = List()

  def removeAt(n: Int, xs: List[Int]): List[Int] = (xs take(n)) ::: (xs drop(n+1))

  def main(args: Array[String]) {
    println(removeAt(1,nums))
    println(removeAt(1,empty))
  }
}
