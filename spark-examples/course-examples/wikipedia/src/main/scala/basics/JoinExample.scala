package basics

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Yogen on 5/28/2017.
  */
object JoinExample {

  val conf: SparkConf = new SparkConf().setAppName("RDD join examples").setMaster("local")
  val sc: SparkContext = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    val userSubs = List((100, ("KTM paper", "Good Terrif")),
      (101, ("PKR paper", "OK Terrif")),
      (102, ("ILM paper", "Good Terrif")),
      (103, ("BTM paper", "Good Terrif")));

    val userSubsRDD = sc.parallelize(userSubs)

    val locations = List(
      (103, "KMM"),
      (104, "KKM"),
      (105, "KTM"),
      (100, "PKR"),
      (101, "PTM")
    )

    val locationRDD = sc.parallelize(locations)

    val customersWithSubscriptionsAndOptionalLocation = userSubsRDD.leftOuterJoin(locationRDD).collect().toList

    customersWithSubscriptionsAndOptionalLocation.foreach(println)

    println("-----------------------------")

    val customersWithLocationDataAndOptionalAbs = userSubsRDD.rightOuterJoin(locationRDD).collect().toList

    customersWithLocationDataAndOptionalAbs.foreach(println)

    sc.stop()
  }
}
