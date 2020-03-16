package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
  * Find total expenses for each customer
  */
object TotalExpenseByCustomer {
  def customerToExpense(line: String) = {
    val fields = line.split(",")
    (fields(0).toInt, fields(2).toFloat)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "TotalExpenseByCustomer")

    val lines = sc.textFile("./data/customer-orders.csv")

    val rdd = lines.map(customerToExpense)

    val expenseByCustomer = rdd.reduceByKey((x, y) => x + y)

    val reversed = expenseByCustomer.map(x => (x._2, x._1)).sortByKey()

    val result = reversed.collect()

    result.foreach(println)
  }
}
