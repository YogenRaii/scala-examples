package recfun

import scala.io.Source

object countchar1{
  def main(args: Array[String]) {
    if (args.length > 0){
      for (line <- Source.fromFile(args(0)).getLines())
        println(line.length +" "+line)
    }
    else Console.err.println("Please enter filenameim")

    val lines = Source.fromFile(args(0)).getLines().toList
    val longestLine = lines.reduceLeft(
      (a,b) => if(a.length > b.length) a else b
    )

    println("Longest Line is: ")
    println(longestLine)
  }
}