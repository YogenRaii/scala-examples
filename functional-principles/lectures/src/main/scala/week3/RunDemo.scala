package week3

object RunDemo {
  def main(args: Array[String]) {
    4 times println("Hello")
  }

  implicit class IntTimes(x: Int) {
    def times[A](f: => A): Unit = {
      def loop(current: Int): Unit =
        if (current > 0) {
          f
          loop(current - 1)
        }

      loop(x)
    }
  }

}
