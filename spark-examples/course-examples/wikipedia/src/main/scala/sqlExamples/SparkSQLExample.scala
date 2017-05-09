package sqlExamples

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
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

//    runBasicDataFrameExample(spark)
//    runDatasetCreationExample(spark)
//    runInferSchemaExample(spark)
    runProgrammaticSchemaExample(spark)

    spark.stop()
  }

  private def runBasicDataFrameExample(spark: SparkSession) : Unit = {
    //for $ use
    import spark.implicits._

    val df = spark.read.json(MockData.filePath("people.json"))
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

    val peopleDS = spark.read.json(MockData.filePath("people.json")).as[Person]
    peopleDS.show()
  }

  def runInferSchemaExample(spark: SparkSession) = {
    import spark.implicits._

    val peopleDF = spark.sparkContext.textFile(MockData.filePath("people.txt"))
                        .map(_.split(","))
                        .map(attribs => Person(attribs(0), attribs(1).trim.toLong))
                        .toDF()
    peopleDF.show()
    peopleDF.createOrReplaceTempView("people")

    val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")

    //access result via field index --> returns name here
    teenagersDF.map(teenager => teenager(0).toString).show()

    // access result via field name
    teenagersDF.map(teenager => teenager.getAs[String]("name")).show()
  }

  def runProgrammaticSchemaExample(spark: SparkSession) = {
    /**
      * 1. Create an RDD of Rows from the original RDD;
      * 2. Create the schema represented by a StructType matching the structure of Rows in the RDD created in Step 1.
      * 3. Apply the schema to the RDD of Rows via createDataFrame method provided by SparkSession.
      */
    import spark.implicits._
    //create RDD
    val peopleRDD = spark.sparkContext.textFile(MockData.filePath("people.txt"))

    //convert RDD(people) to Rows
    val rowRDD = peopleRDD.map(_.split(",")).map(attribs => Row(attribs(0), attribs(1).trim))

    //string encoded schema
    val schemaString = "name age"

    //generate schema based on string of schema
    val fields = schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)

    //apply schema to the RDD
    val peopleDF = spark.createDataFrame(rowRDD, schema)

    //crete temp view from DF
    peopleDF.createOrReplaceTempView("people")

    val results = spark.sql("SELECT name FROM people")

    //results of SQL queries are DF and supports all RDD ops
    results.map(attributes => attributes(0).toString).show()
  }
}
