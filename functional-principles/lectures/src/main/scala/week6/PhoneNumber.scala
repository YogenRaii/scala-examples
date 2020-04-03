package week6

import scala.io.Source

object PhoneNumber {
  val in = Source.fromResource("linuxwords.txt")

  val words = in.getLines().toList filter (word => word forall (_.isLetter))

  val mnem = Map(
    '2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
    '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ"
  )

  val charCode: Map[Char, Char] = for ((digit, str) <- mnem; ltr <- str) yield ltr -> digit

  def wordCode(word: String): String = word.toUpperCase map charCode

  val wordForNum: Map[String, Seq[String]] =
    words groupBy wordCode withDefaultValue Seq()

  def encode(number: String): Set[List[String]] =
    if (number.isEmpty) Set(List())
    else {
      for {
        split <- 1 to number.length
        word <- wordForNum(number take split)
        rest <- encode(number drop split)
      } yield word :: rest
    }.toSet

  def translate(number: String): Set[String] =
    encode(number) map(_ mkString " ")

  def main(args: Array[String]): Unit = {
    println(words.take(10))

    println(wordCode("JAVA"))

    println(encode("7225247386"))
    println(translate("7225247386"))
  }
}
