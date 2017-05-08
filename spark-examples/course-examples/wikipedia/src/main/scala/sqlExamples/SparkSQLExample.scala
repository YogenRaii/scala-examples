package sqlExamples

import org.apache.spark.sql.SparkSession
/**
  * Created by Yogen on 5/8/2017.
  */
object SparkSQLExample {

  case class Person(name: String, age: Long)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
                .appName("Spark SQL basic examples")
                .config("spark.some.config.option", "some-value")
                .master("local")
                .getOrCreate()

    runBasicDataFrameExample(spark)
    runDatasetCreationExample(spark)

    spark.stop()
  }

  private def runBasicDataFrameExample(spark: SparkSession) : Unit = {
    //for $ use
    import spark.implicits._

    val df = spark.read.json(PeopleData.filePath)
    //display content of dataframe to stdout
    df.show()

    //print schema in tree format
    df.printSchema()

    df.select("name").show()

    df.select($"name",$"age"+1).show()

    df.filter($"age" > 21).show()

    df.groupBy("age").count().show()

    //regiser the DataFrame as SQL temp view
    df.createOrReplaceTempView("people")

    val sqlDF = spark.sql("SELECT * FROM people")
    sqlDF.show()
  }


  def runDatasetCreationExample(spark: SparkSession) = {
    import spark.implicits._

    val caseClassDS = Seq(Person("Yogen", 32)).toDS()
    caseClassDS.show()

    val peopleDS = spark.read.json(PeopleData.filePath).as[Person]
    peopleDS.show()
  }
}
