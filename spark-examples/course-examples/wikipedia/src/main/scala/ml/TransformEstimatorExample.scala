package ml

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.linalg.{Vectors, Vector}
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.sql.{Row, SparkSession}
/**
  * Created by Yogen on 5/10/2017.
  */
object TransformEstimatorExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
                            .master("local")
                            .appName("TransformEstimator")
                            .getOrCreate()

    //training set (label, features)
    val training = spark.createDataFrame(Seq(
      (1.0, Vectors.dense(0.0, 1.1, 0.1)),
      (0.0, Vectors.dense(2.0, 1.0, -1.0)),
      (0.0, Vectors.dense(2.0, 1.3, 1.0)),
      (1.0, Vectors.dense(0.0, 1.2, -0.5))
    )).toDF("label", "features")

    //estimator
    val lr = new LogisticRegression()

    println("LogisticRegression params: \n" + lr.explainParams() + "\n")

    //set params with setter methods
    lr.setMaxIter(10)
      .setRegParam(0.01)

    //learning --> Transformer
    val model1 = lr.fit(training)

    println("Model 1 was fit using parameters: " + model1.parent.extractParamMap)

    //another way to set params
    val paramMap = ParamMap(lr.maxIter -> 20)
                      .put(lr.maxIter, 30)
                      .put(lr.regParam -> 0.1, lr.threshold -> 0.55)

    //combine param map
    val paramMap2 = ParamMap(lr.probabilityCol -> "myProbability")
    val paramMapCombined = paramMap ++ paramMap2

    val model2 = lr.fit(training, paramMapCombined)
    println("Model 2 was fit using parameters: " + model2.parent.extractParamMap)

    //test data
    val test = spark.createDataFrame(Seq(
      (1.0, Vectors.dense(-1.0, 1.5, 1.3)),
      (0.0, Vectors.dense(3.0, 2.0, -0.1)),
      (1.0, Vectors.dense(0.0, 2.2, -1.5))
    )).toDF("label", "features")

    model2.transform(test)
          .select("features", "label", "myProbability", "prediction")
          .collect()
          .foreach {
            case Row(features: Vector, label: Double, prob: Vector, prediction: Double) =>
              println(s"($features, $label) -> prob = $prob, prediction = $prediction")
          }

    spark.stop()
  }
}
