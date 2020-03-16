package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
  * Find min temp for each location
  */
object MinTempByLocation {
  def parseLine(line: String) = {
    val fields = line.split(",")
    val location = fields(0)
    val valType = fields(2)
    val temp = fields(3).toFloat * (0.9 / 5) + 32

    (location, valType, temp)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "MinTempByLocation")

    val lines = sc.textFile("./data/1800.csv")

    val rdd = lines.map(parseLine)

    // get all (location, min_temp) -> reduceByVal((x,y) -> Math.min(x,y))
    val locationMinTemp = rdd.filter(_._2 == "TMIN").map(x => (x._1, x._3)).reduceByKey((t1, t2) => Math.min(t1, t2))

    val result = locationMinTemp.collect()

    result.foreach(s => {
      val station = s._1
      val temp = s._2
      val formatted = f"$temp%.2f"
      println(s"$station -> $formatted F")
    })

  }
}
