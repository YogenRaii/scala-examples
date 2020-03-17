package com.eprogrammerz.examples.spark

import java.nio.charset.CodingErrorAction

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

import scala.io.{Codec, Source}

object PopularMoviesV2 {
  def moveNames(): Map[Int, String] = {

    var names: Map[Int, String] = Map()

    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    val lines = Source.fromFile("./data/ml-100k/u.item").getLines()

    for (line <- lines) {
      val fields = line.split('|')
      if (fields(1).length > 1) {
        names += (fields(0).toInt -> fields(1))
      }
    }

    names
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "PopularMovies")

    val lines = sc.textFile("./data/ml-100k/u.data")

    val movies = lines.map(x => (x.split("\t")(1).toInt, 1))

    val sortedMovies = movies.reduceByKey((x, y) => x + y).map(x => (x._2, x._1)).sortByKey()

    val dict = sc.broadcast(moveNames)

    val sortedMoviesWithNames = sortedMovies.map(movie => (dict.value(movie._2), movie._1))

    val results = sortedMoviesWithNames.collect()

    results.foreach(println)
  }
}
