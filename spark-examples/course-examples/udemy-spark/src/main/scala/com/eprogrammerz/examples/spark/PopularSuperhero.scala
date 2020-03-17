package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object PopularSuperhero {
  def friendsCount(line: String) = {
    val fields = line.split("\\s+")

    (fields(0).toInt, fields.length - 1)
  }

  def parseNames(line: String): Option[(Int, String)] = {
    val fields = line.split('\"')

    if (fields.length > 1) {
      Some(fields(0).trim().toInt, fields(1))
    } else {
      None
    }
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "PopularSuperHero")

    val names = sc.textFile("./data/Marvel-names.txt")

    val namesRdd = names.flatMap(parseNames)

    val lines = sc.textFile("./data/Marvel-graph.txt")

    val pairings = lines.map(friendsCount)

    val heroFriends = pairings.reduceByKey((x, y) => x + y)

    val flipped = heroFriends.map(x => (x._2, x._1))

    val mostPopular = flipped.sortByKey(false).take(10)

    mostPopular.foreach(hero => {
      val mostPopularName = namesRdd.lookup(hero._2).head

      println(s"$mostPopularName -> ${hero._1}")
    })

//    val mostPopularName = namesRdd.lookup(mostPopular._2)(0)

//    println(s"$mostPopularName -> ${mostPopular._1}")
  }
}
