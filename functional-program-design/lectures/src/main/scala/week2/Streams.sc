val foodItem = "porridge"

def goldilocks(expr: (String, String)) = expr match {
  case (`foodItem`, _) => "eating"
  case ("chair", "Mama") => "sitting"
  case ("bed", "Baby") => "sleeping"
  case _ => "what?"
}

goldilocks(("porridge", "Papa"))

Stream(1,2,3)

(1 to 10).toStream

// function to generate stream

def generateStream(lo: Int, hi: Int): Stream[Int] =
  if (lo > hi) Stream.empty
  else Stream.cons(lo, generateStream(lo + 1, hi))

generateStream(1, 10)
generateStream(1, 10).take(3) // still gives stream of (head, ?)
generateStream(1, 10).take(3).toList // (1,2,3)

def isPrime(x: Int): Boolean = if (x <= 1) false else (2 to math.sqrt(x).toInt).forall(x % _ != 0)

((100 to 1000).toStream filter isPrime)(1)


// example of infinite stream sequence
def from(n: Int): Stream[Int] = n #:: from(n + 1)

val nats = from(0)

val m4s = nats map (_ * 4)

(m4s take 10).toList

def primes(s: Stream[Int]): Stream[Int] =
  s.head #:: primes(s.tail filter (_ % s.head != 0))

primes(from(2)).take(100).toList


val g: String = "Check out the big brains on Brad!"

g.indexOf('o', 7)

31.toHexString

