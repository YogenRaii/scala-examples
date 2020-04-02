package week2

object CurryingExample {
  def sum(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int) : Int = 
      if(a > b) 0 else f(a) + sumF(a+1, b)
    sumF
  }
  
  def sumInts = sum(x => x)
  
  def sumSquares = sum(x => x*x)
  
  def main(args: Array[String]) : Unit = {
    val sumOfSqrs = sumSquares(1,3)
    println(sumOfSqrs)
  }
}
