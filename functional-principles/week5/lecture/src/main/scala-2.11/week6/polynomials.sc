class Poly1(val terms: Map[Int,Double]){
  def + (other:Poly1) = new Poly1(terms ++ (other.terms map adjust))
  def adjust(term: (Int,Double)):(Int,Double) = {
    val (exp,coeff) = term
    terms get exp match {
      case Some(coeff1) => exp -> (coeff + coeff1)
      case None => exp -> coeff
    }
  }
  override def toString =
    (for ((exp,coeff) <- terms.toList.sorted.reverse) yield coeff + " x^"+exp) mkString " + "
}

class Poly(terms0: Map[Int,Double]){
  val terms = terms0 withDefaultValue 0.0
  def + (other:Poly) = new Poly(terms ++ (other.terms map adjust))
  def adjust(term: (Int,Double)):(Int,Double) = {
    val (exp,coeff) = term
    exp -> (coeff + terms(exp))
  }
  override def toString =
    (for ((exp,coeff) <- terms.toList.sorted.reverse) yield coeff + " x^"+exp) mkString " + "
}

val p1 = new Poly(Map(1 -> 2.0, 3-> 4.0, 5 -> 6.32))
val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))
p1 + p2

p1.terms(7)
