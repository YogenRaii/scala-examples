package com.eprogrammerz.examples.scala.obj

object InSetObject {
  abstract class InSet {
    def contains(x: Int): Boolean
    def incl(x: Int): InSet
    def union(that: InSet): InSet
  }

  object Empty extends InSet {
    override def contains(x: Int): Boolean = false

    override def incl(x: Int): InSet = new NonEmpty(x, Empty, Empty)

    override def toString: String = "."

    override def union(that: InSet): InSet = that
  }

  class NonEmpty(e: Int, left: InSet, right: InSet) extends InSet {
    override def contains(x: Int): Boolean = {
      if (x < e) left contains x
      else if (x > e) right contains x
      else true
    }

    override def incl(x: Int): InSet = {
      if (x < e) new NonEmpty(e, left.incl(x), right)
      else if (x > e) new NonEmpty(e, left, right.incl(x))
      else this
    }

    override def toString: String = "{" + left + e + right + "}"

    override def union(that: InSet): InSet = {
      ((left union right) union that) incl e
    }
  }

  def main(args: Array[String]): Unit = {
    val t1 = new NonEmpty(3, Empty, Empty)
    val t2 = t1 incl 4
    println(t2)

    val t3 = new NonEmpty(5, Empty, Empty)
    val t4 = t2 union t3
    println(t4)
  }

}
