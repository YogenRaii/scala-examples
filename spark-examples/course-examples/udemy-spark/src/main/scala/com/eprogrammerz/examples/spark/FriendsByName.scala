package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
  * Find average number of friends for each same name
  */
object FriendsByName {
  def parseLine(line: String): (String, Int) = {
    val fields = line.split(",")

    val name = fields(1)
    val friends = fields(3).toInt
    (name, friends)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "FriendsByName")

    val lines = sc.textFile("./data/fakeFriends.csv")

    val rdd = lines.map(parseLine) // (yogen, 20), (pratima, 3), (yogen, 2) -> (yogen, (22, 2)), (pratima, (3,1) -> (yogen, 11), (pratima, 3)

    val avgByName = rdd.mapValues(x => (x, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2)).mapValues(x => x._1 / x._2)

    val result = avgByName.collect()

    result.foreach(println)
  }
}
