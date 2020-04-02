def scaleList(list: List[Double], factor: Double): List[Double] = list match {
  case Nil => Nil
  case x :: list1 => x * factor :: scaleList(list1, factor)
}

scaleList(List(3, 2, 5), 4)
scaleList(List(), 3)

def squareList(list: List[Double]): List[Double] =
  list map (x => x * x)

squareList(List(1, 2, 3, 4))
squareList(List())

val ints = List(1, -3, 4, -6, 6)
val fruits = List("banana", "apple", "pineapple", "orange")

ints filter (x => x > 0)
ints partition (x => x > 0)
ints dropWhile (x => x > 0)


def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: xs1 =>
    val (first, remaining) = xs span (y => y == x)
    first :: pack(remaining)
}

val data = List('a', 'a', 'a', 'b', 'c', 'c', 'b')
pack(data)

def encode[T](xs: List[T]): List[(T, Int)] = {
  pack(xs) map (x => (x.head, x.size))
}

encode(data)


def sum(xs: List[Int]): Int = 0 :: xs reduceLeft ((x, y) => x + y)
def sum1(xs: List[Int]): Int = 0 :: xs reduceLeft (_ + _)

sum(ints)
sum1(ints)
sum(List())

def max(xs: List[Int]): Int = xs.max

max(List(3, 2, 5, 3))

def isPrime(n: Int): Boolean = if (n <= 1) false else (2 until n) forall (d => n % d != 0)

(1 to 10).foreach(x => println(isPrime(x)))

