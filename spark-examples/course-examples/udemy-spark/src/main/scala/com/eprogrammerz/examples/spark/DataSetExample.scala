package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
  * type DataFrame = Dataset[ROW]
  */
object DataSetExample {
  case class Listing(stree: String, zip: Int, price: Int)

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "DataSetExample")

    val spark = SparkSession.builder().config(sc.getConf).getOrCreate()

    val listings = List(Listing("frisco", 75036, 12), Listing("Plano", 55093, 15), Listing("frisco", 75036, 2), Listing("Allen", 75093, 3))

    import spark.implicits._

    val listingDf = sc.parallelize(listings).toDF

    // average prices for each zip

    val avgPricesDf = listingDf.groupBy(listingDf("zip")).avg("price")

    avgPricesDf.head.schema.printTreeString()

    val avgPrices = avgPricesDf.map {
      row => (row(0).asInstanceOf[Int], row(1).asInstanceOf[Double])
    }.collect()

    avgPrices.foreach(println)


    // another example
    println("----------")
    val keyValues = List((1, "aa"), (2, "bb"), (1, "ee"), (2, "cc"))

    val ds = keyValues.toDS

    val grouped = ds.groupByKey(_._1).mapGroups((k, v) => (k, v.foldLeft("")((acc, curr) => acc + curr._2)))

    grouped.show()

    println("Better")

    val grouped2 = ds.groupByKey(_._1).mapValues(_._2).reduceGroups(_ + _)

    grouped2.show()
  }
}
