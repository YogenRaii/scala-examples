package com.eprogrammerz.examples.scala.basics.worksheets

object BasicsWorksheet {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(127); 
  println("Welcome to the Scala worksheet");$skip(33); 
  
  var name: String = "Yogen";System.out.println("""name  : String = """ + $show(name ));$skip(34); ;
  var character: String = "Good";System.out.println("""character  : String = """ + $show(character ));$skip(43); ;
  println(s"$name is not $character guy");}
}
