1 + 2

def isSafe(col: Int, queens: List[Int]): Boolean = {
  val row = queens.length
  val queenWithRows = (row - 1 to 0 by -1) zip queens
  queenWithRows forall {
    case (r, c) => c != col && math.abs(col - c) != row - r
  }
}


def nqueens(n: Int): Set[List[Int]] = {

  def placeQueen(k: Int): Set[List[Int]] = {
    if (k == 0) Set(List())
    else
      for {
        queens <- placeQueen(k - 1)
        col <- 0 until n
        if isSafe(col, queens)
      } yield col :: queens
  }

  placeQueen(n)
}

def show(queens: List[Int]): String = {
  val lines = for (col <- queens.reverse) yield Vector.fill(queens.length)("* ").updated(col, "X ").mkString
  "\n" + (lines mkString "\n")
}

(nqueens(4) map show) mkString "\n"


