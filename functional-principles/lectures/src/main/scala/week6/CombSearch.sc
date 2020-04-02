
def isPrime(n: Int): Boolean = (2 until n) forall (n % _ != 0)
def findPrimePairs(n: Int): Seq[(Int, Int)] = {
  (1 until n) flatMap (i =>
    (1 until i) map (j => (i, j))) filter (pair => isPrime(pair._1 + pair._2))
}

findPrimePairs(7)

val n = 7
((1 until n) flatMap (i =>
  (1 until i - 1) map (j => (i, j))))

(1 until n) flatMap (i =>
  (1 until i) map (j => (i, j))) filter (pair => isPrime(pair._1 + pair._2))

for {
  i <- 1 until n
  j <- 1 until i
  if isPrime(i + j)
} yield (i, j)

def scalarProduct(xs: List[Double], ys: List[Double]): Double =
  (for ((x, y) <- xs zip ys) yield x * y).sum

scalarProduct(List(2.3, 4.2), List(1, 2))



val xs = List(1, 2, 3)
val ys = List(3, 2, 1)
(xs zip ys) map (pair => pair._1 * pair._2)

xs map (x => x * 2)
xs map (_ * 2)

def scalarProduct1(xs: List[Double], ys: List[Double]): Double =
  ((xs zip ys) map (pair => pair._1 * pair._2)).sum

scalarProduct1(List(2.3, 4.2), List(1, 2))
