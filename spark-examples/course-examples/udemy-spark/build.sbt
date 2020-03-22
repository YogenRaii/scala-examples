name := "udemy-spark"

version := "0.1"

scalaVersion := "2.12.11"

libraryDependencies ++= Seq("org.apache.spark" %% "spark-sql" % "2.4.5",
                           "org.apache.spark" %% "spark-mllib" % "2.4.5")
