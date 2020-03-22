package com.eprogrammerz.examples.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession

/**
  * Example of linear regression model training and testing
  */
object LinearRegressionDF {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder().master("local[*]").appName("LinearRegressionDF").getOrCreate()

    val lines = spark.sparkContext.textFile("./data/regression.txt")

    // label -> more than one features
    // i.e. key -> vector
    val data = lines.map(_.split(",")).map(x => (x(0).toDouble, Vectors.dense(x(1).toDouble)))

    // convert rdd to df
    import spark.implicits._
    val colNames = Seq("label", "features")
    val df = data.toDF(colNames: _*)


    // split df to training and testing data
    val trainTest = df.randomSplit(Array(0.5, 0.5))

    val trainDf = trainTest(0)
    val testDf = trainTest(1)

    val lrm = new LinearRegression()
      .setRegParam(0.2) // regularization
      .setElasticNetParam(0.8) // elastic net param
      .setMaxIter(1000)
      .setTol(1E-6) // tolerance

    // train model with training data set
    val model = lrm.fit(trainDf)

    // see if we can predict with test data
    // this adds "prediction" column in df
    val predictions = model.transform(testDf).cache()

    // extract predictions and correct "known" values
    val predictionsAndLabels = predictions.select("prediction", "label").rdd.map(x => (x.getDouble(0), x.getDouble(1)))

    predictionsAndLabels.foreach(println)

    spark.stop()
  }
}
