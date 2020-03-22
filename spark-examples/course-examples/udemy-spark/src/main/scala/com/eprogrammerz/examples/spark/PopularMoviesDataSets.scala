package com.eprogrammerz.examples.spark

import java.nio.charset.CodingErrorAction

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions.desc
import scala.io.{Codec, Source}

object PopularMoviesDataSets {
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

  final case class Movie(MovieId: Int)

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().master("local[*]").appName("PopularMoviesDS").getOrCreate()

    val lines = spark.sparkContext.textFile("./data/ml-100k/u.data").map(x => Movie(x.split("\t")(1).toInt))

    import spark.implicits._
    val moviesDS = lines.toDS

    // top movie ids
    val topMovies = moviesDS.groupBy("MovieId").count().orderBy(desc("count")).cache()

    topMovies.show()

    val results = topMovies.take(10)

    val movieNames = moveNames()

    results.foreach(movie => {
      val movieId = movie(0).asInstanceOf[Int]
      val movieName = movieNames(movieId)
      val count = movie(1).asInstanceOf[Long]
      println(s"$movieId\t$movieName\t$count")
    })

    spark.stop()
  }
}
