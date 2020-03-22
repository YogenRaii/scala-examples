package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

/**
  * SparkSQL examples
  */
object SparkSQLDS {

  case class Person(id: Int, name: String, age: Int, numFriends: Int)

  def mapper(line: String): Person = {
    val fields = line.split(",")

    val person: Person = Person(fields(0).toInt, fields(1), fields(2).toInt, fields(3).toInt)

    person
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().appName("SparkSQLDS").master("local[*]").getOrCreate()

    // first create rdd so that data can be structured
    val lines = spark.sparkContext.textFile("./data/fakefriends.csv")

    /**
      * root
      * |-- id: integer (nullable = false)
      * |-- name: string (nullable = true)
      * |-- age: integer (nullable = false)
      * |-- numFriends: integer (nullable = false)
      */
    import spark.implicits._

    val people = lines.map(mapper).toDS.cache()

    people.printSchema()

    people.select("name").show()

    people.filter(people("age") >= 13 && people("age") <= 19).show()

    people.groupBy(people("age")).count().orderBy("age").show()

    spark.stop()
  }
}
