package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
  * join, leftOuterJoin and rightOuterJoin are transformations on rdds of pairs
  */
object JoinExample {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local[*]", "JoinExample")

    // users who subscribed the service
    val users = List((101, ("Rai", 12)), (102, ("Bhujel", 10)), (103, ("Khaling", 15)), (104, ("Thulung", 16)))

    // locations from where they subscribed
    val locations = List((101, "USA"), (102, "Nepal"), (103, "AUS"), (105, "Siberia"))

    val userRdd = sc.parallelize(users)

    val locationRdd = sc.parallelize(locations)

    // users who has subscribed and has entered location
    val usersWithLocation = userRdd.join(locationRdd)

    /**
      * (101,((Rai,12),USA))
      * (102,((Bhujel,10),Nepal))
      * (103,((Khaling,15),AUS))
      */
    usersWithLocation.collect().foreach(println)

    println("----------------")

    // all users who subscribed and their location
    // def leftOuterJoin[W](other: RDD[(K, W)]): RDD[(K, (V, Option[W]))]
    val usersWithOptionalLocation = userRdd.leftOuterJoin(locationRdd)

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
    val locationWithOptionalUSer = userRdd.rightOuterJoin(locationRdd)

    /**
      * (105,(None,Siberia))
      * (101,(Some((Rai,12)),USA))
      * (102,(Some((Bhujel,10)),Nepal))
      * (103,(Some((Khaling,15)),AUS))
      */
    locationWithOptionalUSer.collect().foreach(println)
  }
}
