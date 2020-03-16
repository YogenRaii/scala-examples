package com.eprogrammerz.examples.scala.basics

object HelloWorld {
  def main(args:Array[String]) {
    println("Hello World!!!");
    
    val name: String = "Yogen";
    
    println(name);
    
    val pt = new Point(23,20);
    pt.move(2, -3);
    pt.print();
  }
}

class Point(val xc: Int, val yc:Int) {
  var x: Int = xc;
  var y: Int = yc;
  
  def move(dx:Int, dy: Int) {
    x = x + dx;
    y = y + dy;
  }
  
  def print() {
    println("x + dx: " + x);
    println("y + dy: " + y);
  }
}