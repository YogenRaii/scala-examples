package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object WordCount {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "WordCount")

    val lines = sc.textFile("./data/book.txt")

//    val wordCounts = lines.flatMap(x => x.split("\\W+")).map(x => x.toLowerCase()).countByValue()
    val wordCounts = lines.flatMap(x => x.split("\\W+")).map(x => x.toLowerCase()).map(x => (x, 1)).reduceByKey((x, y) => x + y)

    val countWords = wordCounts.map(x => (x._2, x._1)).sortByKey()

    countWords.foreach(println)
  }
}
