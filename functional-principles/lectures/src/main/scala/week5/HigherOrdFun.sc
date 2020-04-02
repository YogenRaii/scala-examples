def scalaList(list: List[Double], factor: Double): List[Double] = list match {
  case Nil => Nil
  case x :: xs => x * factor :: scalaList(xs, factor)
}

scalaList(List(3, 2, 5), 4)
scalaList(List(), 3)

def squareList(list: List[Double]): List[Double] =
  list map (x => x * x)

squareList(List(1, 2, 3, 4))
squareList(List())

val ints = List(1, -3, 4, -6, 6)
val fruits = List("banana", "apple", "pineapple", "orange")

ints filter (x => x > 0)
ints partition (x => x > 0)
ints dropWhile (x => x > 0)

/**
  * List('a', 'a', 'a', 'b', 'c', 'c', 'b') => List(List('a','a','a'), List('b'), List('c','c'), List('b'))
  *
  * @param xs
  * @tparam T
  * @return
  */
def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: _ =>
    val (first, remaining) = xs span (y => y == x)
    first :: pack(remaining)
}

val data = List('a', 'a', 'a', 'b', 'c', 'c', 'b')
pack(data)

/**
  *  List(List('a','a','a'), List('b'), List('c','c'), List('b')) => List(('a',3), ('b',1), ('c', 2), ('a', 1))
  *
  * @param xs
  * @tparam T
  * @return
  */
def encode[T](xs: List[T]): List[(T, Int)] = {
  pack(xs) map (x => (x.head, x.size))
}

encode(data)

/**
  * List(x1, x2,..., xn) reduceLeft op = (..(x1 op x2) op ..) op xn
  * @param xs
  * @return
  */
def sum(xs: List[Int]): Int = 0 :: xs reduceLeft ((x, y) => x + y)
def product(xs: List[Int]): Int = 1 :: xs reduceLeft (_ * _)

sum(ints)
product(ints)
sum(List())

def productWithFold(xs: List[Int]): Int = (ints foldLeft 1) (_ * _)
productWithFold(ints)

def max(xs: List[Int]): Int = xs.max

max(List(3, 2, 5, 3))

def isPrime(n: Int): Boolean = if (n <= 1) false else (2 until n) forall (d => n % d != 0)

(1 to 10).foreach(x => println(isPrime(x)))

