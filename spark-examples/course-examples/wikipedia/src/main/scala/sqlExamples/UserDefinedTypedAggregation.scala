package sqlExamples

import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}

/**
  * Created by Yogen on 5/9/2017.
  */
object UserDefinedTypedAggregation {
  case class Employee(name: String, salary: Long)

  case class Average(var sum: Long, var count: Long)

  object MyAverage extends Aggregator[Employee, Average, Double]{
    def zero: Average = Average(0L, 0L)

    //for performance, modify buffer instead of creating new object
    def reduce(buffer: Average, employee: Employee): Average = {
      buffer.sum += employee.salary
      buffer.count += 1
      buffer
    }

    //merge two immediate values
    def merge(b1: Average, b2: Average) : Average = {
      b1.sum += b2.sum
      b1.count += b2.count
      b1
    }

    //transform o/p of reduction
    def finish(reduction: Average): Double = reduction.sum.toDouble / reduction.count

    //specifies the Encoder for the intermediate value type
    def bufferEncoder: Encoder[Average] = Encoders.product

    //specifies the Encoder for the final o/p value type
    def outputEncoder: Encoder[Double] = Encoders.scalaDouble
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
                            .appName("User Defined Typed Aggregation")
                            .master("local[3]")
                            .getOrCreate()

    import spark.implicits._

    val ds = spark.read.json(MockData.filePath("employee.json")).as[Employee]
    ds.show()

    val averageSalary = MyAverage.toColumn.name("average_salary")
    val result = ds.select(averageSalary)
    result.show()

    //closing the spark context/session
    spark.stop()
  }
}
