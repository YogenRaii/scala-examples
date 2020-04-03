package week6

import week6.PolynomialBetter.Poly
import week6.PolynomialWOMap.Poly

object Polynomial {
  class Poly(val terms: Map[Int, Double]) {
    def +(other: Poly) = new Poly(terms ++ (other.terms map adjust))

    def adjust(term: (Int, Double)): (Int, Double) = {
      val (exp, coeff) = term
      terms get exp match {
        case Some(coeff1) => exp -> (coeff + coeff1)
        case None => exp -> coeff
      }
    }

    override def toString: String =
      (for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + " x^" + exp) mkString " + "
  }

  def main(args: Array[String]): Unit = {
    val p1 = new Poly(Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.32))
    val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))
    println(p1 + p2)
  }
}

object PolynomialBetter {

  class Poly(terms0: Map[Int, Double]) {
    val terms: Map[Int, Double] = terms0 withDefaultValue 0.0

    def +(other: Poly) = new Poly(terms ++ (other.terms map adjust))

    def adjust(term: (Int, Double)): (Int, Double) = {
      val (exp, coeff) = term
      exp -> (coeff + terms(exp))
    }

    override def toString: String =
      (for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + " x^" + exp) mkString " + "
  }

  def main(args: Array[String]): Unit = {
    val p1 = new Poly(Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.32))
    val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))
    println(p1 + p2)
  }
}

object PolynomialWOMap {
  class Poly(terms0: Map[Int, Double]) {
    // var args
    def this(bindings: (Int, Double)*) = this(bindings.toMap)

    val terms: Map[Int, Double] = terms0 withDefaultValue 0.0

    def + (other: Poly) = new Poly(terms ++ (other.terms map adjust))

    def adjust(term: (Int, Double)) : (Int, Double) = {
      val (exp, coeff) = term
      exp -> (coeff + terms(exp))
    }

    override def toString: String = (for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + " x^" + exp) mkString " + "
  }

  def main(args: Array[String]): Unit = {
    val p1 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.32)
    val p2 = new Poly(0 -> 3.0, 3 -> 7.0)
    println(p1 + p2)
  }
}

object PolynomialWFold {
  class Poly(terms0: Map[Int, Double]) {
    def this(bindings: (Int, Double)*) = this(bindings.toMap)

    val terms: Map[Int, Double] = terms0 withDefaultValue 0.0

    def + (other: Poly): Poly = new Poly((other.terms foldLeft terms)(addTerm))

    def addTerm(terms: Map[Int, Double], term: (Int, Double)): Map[Int, Double] = {
      val (exp, coeff) = term
      terms + (exp -> (coeff + terms(exp)))
    }

    override def toString: String = (for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + " x^" + exp) mkString " + "
  }

  def main(args: Array[String]): Unit = {
    val p1 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.32)
    val p2 = new Poly(0 -> 3.0, 3 -> 7.0)
    println(p1 + p2)
  }
}
