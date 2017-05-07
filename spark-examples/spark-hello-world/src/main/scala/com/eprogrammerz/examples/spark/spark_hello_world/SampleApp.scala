package com.eprogrammerz.examples.spark.spark_hello_world

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SampleApp {
  def main(args: Array[String]) {
    val logFile = "C:/Yogen/Downloaded/spark/README.md"
    val conf = new SparkConf().setAppName("Hello world application")
    val sc = new SparkContext(conf)
    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter( line => line.contains("a")).count
    val numBs = logData.filter(line => line.contains("b")).count
    println(s"Line with a: $numAs, Lines with b: $numBs")
    sc.stop()
  }
}