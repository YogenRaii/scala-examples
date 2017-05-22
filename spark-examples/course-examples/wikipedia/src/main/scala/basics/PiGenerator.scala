package basics

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Yogen on 5/8/2017.
  */
object PiGenerator {

  /**
    * This is example for estimating value of PI
    */
  val NUM_SAMPLES: Int = 10000
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("PI Generator").setMaster("local[4]")
    val sc: SparkContext = new SparkContext(conf)
    val pi = generatePI(sc, NUM_SAMPLES)

    println(s"PI is roughly $pi")

    sc.stop()
  }

  def generatePI(sc: SparkContext, NUM_SAMPLES: Int) = {
    val count = sc.parallelize(1 to NUM_SAMPLES).filter{ _ =>
      val x = math.random
      val y = math.random

      x*x + y*y < 1
    }.count()

    4.0 * count/NUM_SAMPLES
  }

}
