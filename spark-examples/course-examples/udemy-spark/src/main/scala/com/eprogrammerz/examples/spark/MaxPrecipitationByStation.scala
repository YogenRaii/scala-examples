package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
  * Find max precipitation for each station
  */
object MaxPrecipitationByStation {
  def parseLine(line: String) = {
    val fields = line.split(",")
    val station = fields(0)
    val valType = fields(2)
    val temp = fields(3).toFloat

    (station, valType, temp)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "MaxPrecipitationByStation")

    val lines = sc.textFile("./data/1800.csv")

    val rdd = lines.map(parseLine)

    val maxPrecipitationByStation = rdd.filter(_._2 == "PRCP").map(x => (x._1, x._3)).reduceByKey((p1, p2) => Math.max(p1, p2))

    val result = maxPrecipitationByStation.collect()

    result.foreach(println)
  }
}
