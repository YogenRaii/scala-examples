var capital = Map("USA" -> "Washigton DC", "npela" -> "KTM")
println(capital)

capital += ("Japan" -> "Tokyo")

println(capital)

def factorial(x: BigInt): BigInt = {
  if (x == 0) 1 else x * factorial(x - 1)
}

factorial(30)

import scala.collection.mutable.Map

val treasureMap = Map[Int, String]()
treasureMap += (1 -> "Go to island.")
treasureMap += (2 -> "Yogen")
treasureMap += (3 -> "Dig.")

println(treasureMap(2))

def printArgs(args: Array[String]): Unit = {
  args.foreach(println)
  //  for(arg <- args){
  //    print("jpt")
  //    println(arg)
  //  }
}

val names = Array("Yogen", "Rai")
printArgs(names)
println()
