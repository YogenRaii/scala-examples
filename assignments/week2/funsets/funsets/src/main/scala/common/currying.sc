import scala.annotation.tailrec

object currying{
  def product(f:Int => Int)(a:Int,b:Int):Int = {
    /*def loop(a:Int,p:Int):Int = {
      if (a>b)  p
      else loop(a+1,f(a)*p)
    }
    loop(a,1)*/
    if (a>b) 1
    else f(a) * product(f)(a+1,b)
  }
  product(x => x*x)(3,4)

  def fact(n:Int) = product(x => x)(1,n)

  fact(4)

  def mapReduce(f:Int => Int, combine:(Int,Int) => Int,zero:Int)(a:Int,b:Int):Int =
    if (a>b) zero
    else combine(f(a),mapReduce(f,combine,zero)(a+1,b))

  def productNew(f:Int =>Int)(a:Int,b:Int) = mapReduce(f,(x,y)=>x*y,1)(a,b)
  productNew(x =>x)(3,4)

  def factNew(f:Int => Int)(n:Int) = mapReduce(f,(n,p) => n*p,1 )(1,n)
  factNew(x =>x)(4)
}