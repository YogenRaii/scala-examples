package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
    * Exercise 1
    */
  def pascal(c: Int, r: Int): Int = {
    if (r == c || c == 0) 1
    else pascal(c, r - 1) + pascal(c - 1, r - 1)
  }

  /**
    * Exercise 2
    */
  def balance(chars: List[Char]): Boolean = {
    def balanced(chars: List[Char], count: Int): Boolean = chars match {
      case Nil => count == 0
      case _ if chars.head == '(' => balanced(chars.tail, count + 1)
      case _ if chars.head == ')' => count > 0 && balanced(chars.tail, count - 1)
      case _ => balanced(chars.tail, count)
    }

    balanced(chars, 0)
  }

  /**
    * Exercise 3
    */
  def countChange(money: Int, coins: List[Int]): Int = {
    def recurs(m: Int, cs: List[Int], count: Int): Int = {
      if (m < 0) count
      else if (cs.isEmpty) {
        if (m == 0) count + 1
        else count
      }
      else recurs(m, cs.tail, count) + recurs(m - cs.head, cs, count)
    }

    recurs(money, coins, 0)
  }
}
