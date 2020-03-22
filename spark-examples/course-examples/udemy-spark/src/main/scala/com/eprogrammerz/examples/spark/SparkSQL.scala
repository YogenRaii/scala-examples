package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

/**
  * SparkSQL examples
  */
object SparkSQL {

  case class Person(id: Int, name: String, age: Int, numFriends: Int)

  def mapper(line: String): Person = {
    val fields = line.split(",")

    val person: Person = Person(fields(0).toInt, fields(1), fields(2).toInt, fields(3).toInt)

    person
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().appName("SparkSQL").master("local[*]").getOrCreate()

    // first create rdd so that data can be structured
    val lines = spark.sparkContext.textFile("./data/fakefriends.csv")

    val people = lines.map(mapper)

    /**
      * root
      * |-- id: integer (nullable = false)
      * |-- name: string (nullable = true)
      * |-- age: integer (nullable = false)
      * |-- numFriends: integer (nullable = false)
      */
    import spark.implicits._
    val schemaPeople = people.toDS

    schemaPeople.printSchema()

    schemaPeople.createTempView("people")

    val teenagers = spark.sql("select * from people where age >= 13 and age <= 19")

    val result = teenagers.collect()

    result.foreach(println)

    spark.stop()
  }
}
