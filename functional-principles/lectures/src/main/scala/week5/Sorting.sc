/**
  * Handles List of Int
  *
  * @param xs
  * @return
  */
def msort(xs: List[Int]): List[Int] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
      case (Nil, y) => y
      case (x, Nil) => x
      case (x :: xs1, y :: ys1) =>
        if (x < y) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }

    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}

msort(List(1, 3, 2, 5, 4))

/**
  * Generic version of merge sort with type parametrization
  *
  * @param xs
  * @param lt
  * @tparam T
  * @return
  */
def msortBetter[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, ys1) => ys1
      case (xs1, Nil) => xs1
      case (x :: xs1, y :: ys1) =>
        if (lt(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }

    val (fst, snd) = xs splitAt n
    merge(msortBetter(fst)(lt), msortBetter(snd)(lt))
  }
}

msortBetter(List(1, 3, 2, 5, 4))((x: Int, y: Int) => x < y)

val fruits = List("banana", "apple", "pineapple", "orange")

msortBetter(fruits)((x: String, y: String) => x.compareTo(y) < 0)


/**
  * Sort with ordering
  *
  * Parametrization with Ordered
  */
def msortOrder[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, ys1) => ys1
      case (xs1, Nil) => xs1
      case (x :: xs1, y :: ys1) =>
        if (ord.lt(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }

    val (fst, snd) = xs splitAt n
    merge(msortOrder(fst), msortOrder(snd))
  }
}
msortOrder(List(1, 3, 2, 5, 4)) // no need to pass Ordering for asc
msortOrder(List(1, 3, 2, 5, 4))(Ordering[Int].reverse)

msortOrder(fruits)

