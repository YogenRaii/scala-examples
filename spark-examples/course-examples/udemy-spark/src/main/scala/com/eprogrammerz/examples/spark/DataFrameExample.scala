package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
  * join, left_outer and right_outer are transformations on rdds of pairs
  */
object DataFrameExample {
  case class Abo(id: Int, v: (String, Int))
  case class Loc(id: Int, v: String)

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "DataFrameExample")

    // users who subscribed the service
    val users = List(Abo(101, ("Rai", 12)), Abo(102, ("Bhujel", 10)), Abo(103, ("Khaling", 15)), Abo(104, ("Thulung", 16)))

    // locations from where they subscribed
    val locations = List(Loc(101, "USA"), Loc(102, "Nepal"), Loc(103, "AUS"), Loc(105, "Siberia"))

    val spark = SparkSession.builder().config(sc.getConf).getOrCreate()

    import spark.implicits._

    val userDf = sc.parallelize(users).toDF

    val locationDf = sc.parallelize(locations).toDF

    // users who has subscribed and has entered location
    val usersWithLocation = userDf.join(locationDf, userDf("id") === locationDf("id"))

    /**
      * (101,((Rai,12),USA))
      * (102,((Bhujel,10),Nepal))
      * (103,((Khaling,15),AUS))
      */
    usersWithLocation.collect().foreach(println)

    println("----------------")

    // all users who subscribed and their location
    // def leftOuterJoin[W](other: RDD[(K, W)]): RDD[(K, (V, Option[W]))]
    val usersWithOptionalLocation = userDf.join(locationDf, userDf("id") === locationDf("id"), "left_outer")

    /**
      * (104,((Thulung,16),None))
      * (101,((Rai,12),Some(USA)))
      * (102,((Bhujel,10),Some(Nepal)))
      * (103,((Khaling,15),Some(AUS)))
      */
    usersWithOptionalLocation.collect().foreach(println)

    println("----------------")

    // all locations entered into by subscribing users
    // def rightOuterJoin[W](other: RDD[(K, W)]): RDD[(K, (Option[V], W))]
    val locationWithOptionalUSer = userDf.join(locationDf, userDf("id") === locationDf("id"), "right_outer")

    /**
      * (105,(None,Siberia))
      * (101,(Some((Rai,12)),USA))
      * (102,(Some((Bhujel,10)),Nepal))
      * (103,(Some((Khaling,15)),AUS))
      */
    locationWithOptionalUSer.collect().foreach(println)
  }
}
