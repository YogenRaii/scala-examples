package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object PopularMovies {
  def moveRating(line: String) = {
    val fields = line.split(",")
    (fields(1).toInt, fields(2).toInt)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "PopularMovies")

    val lines = sc.textFile("./data/ml-100k/u.data")

    val movies = lines.map(x => (x.split("\t")(1).toInt, 1))

    val sortedMovies = movies.reduceByKey((x, y) => x + y).map(x => (x._2, x._1)).sortByKey()

    val results = sortedMovies.collect()

    results.foreach(println)
  }
}
