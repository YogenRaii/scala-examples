package basics

/**
  * Created by Yogen on 5/28/2017.
  *
  * groupBy --> def groupBy[K](f:A => K) : Map[K, Traversable[A]]
  */
object RddExampls {

  def main(args: Array[String]): Unit = {
    val ages = List(2, 52, 44, 23, 17, 14, 12, 82, 51, 64)

    val grouped = ages.groupBy {
      age =>
        if (age >= 18 && age < 65) "adult"
        else if (age < 18) "child"
        else "senior"
    }

    println(grouped)

    println(grouped.get("adult"))
  }
}
