package week1

object RandomGenerator {
  trait Generator[+T] {
    self =>

    def generate: T

    def map[S](f: T => S): Generator[S] = new Generator[S] {
      override def generate = f(self.generate)
    }

    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      override def generate = f(self.generate).generate
    }
  }

  trait Tree

  case class Leaf(x: Int) extends Tree

  case class Inner(left: Tree, right: Tree) extends Tree

  // need to write a function that generates random tree

  def integers: Generator[Int] = new Generator[Int] {
    override def generate = scala.util.Random.nextInt()
  }

  def booleans: Generator[Boolean] = integers map(_ >= 0)

  def leafs: Generator[Leaf] = for {
    x <- integers
  } yield Leaf(x)

  def inners: Generator[Inner] = for {
    l <- trees
    r <- trees
  } yield Inner(l, r)


  def trees: Generator[Tree] = for {
    isLeaf <- booleans
    tree <- if (isLeaf) leafs else inners
  } yield tree

  def main(args: Array[String]): Unit = {
    println(trees)
  }

}
