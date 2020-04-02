package com.eprogrammerz.examples.scala.obj

/**
  * pattern matching uses case class and looks into constructor to match the value
  */
object PatternMatching {
  trait Expr {

    // if need to extend many classes, then it doesn't make sense,
    // but if you want to add more methods, then it works
    def eval: Int = this match {
      case Number(n) => n
      case Sum(e1, e2) => e1.eval + e2.eval
      case Prod(e1, e2) => e1.eval * e2.eval
    }

    def show: String = this match {
      case Number(n) => n.toString
      case Sum(e1, e2) => "("+ e1.show + " + " + e2.show + ")"
      case Prod(e1, e2) => "("+ e1.show + " * " + e2.show + ")"
    }
  }

  case class Number(n: Int) extends Expr

  case class Sum(e1: Expr, e2: Expr) extends Expr

  case class Prod(e1: Expr, e2: Expr) extends Expr

  /*def eval(e: Expr): Int = e match {
    case Number(n) => n
    case Sum(e1, e2) => eval(e1) + eval(e2)
  }*/

  def main(args: Array[String]): Unit = {
    val e1: Expr = Number(2)
    val e2: Expr = Sum(e1, Number(5))
    val e3: Expr = Sum(e1, Sum(e2, Number(1)))

//    println(eval(e1))
//    println(eval(e2))

    println(e1.eval)
    println(e2.eval)
    println(e3.eval)

    println(e1.show)
    println(e2.show)
    println(e3.show)

    val p1: Expr = Sum(Prod(Number(2), Number(3)), Number(5)) // 2 * 3 + 5
    val p2: Expr = Prod(Sum(Number(2), Number(3)), Number(5)) // (2 + 3) * 5

    println(p1.show)
    println(p2.show)
  }
}
