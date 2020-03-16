package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object FriendsByAge {
  def parseLine(line: String): (Int, Int) = {
    val fields = line.split(",")
    val age = fields(2).toInt
    val numOfFriends = fields(3).toInt
    (age, numOfFriends)
  }

  def main(args: Array[String]): Unit = {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a SparkContext using every core of the local machine, named RatingsCounter
    val sc = new SparkContext("local[*]", "FriendsByAge")

    val lines = sc.textFile("./data/fakefriends.csv")

    val rdd = lines.map(parseLine)

    val totalByAge = rdd.mapValues(s => (s, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2)).mapValues(x => x._1 / x._2)

    val result = totalByAge.collect().sortBy(x => x._2).reverse

    result.foreach(println)

  }
}
