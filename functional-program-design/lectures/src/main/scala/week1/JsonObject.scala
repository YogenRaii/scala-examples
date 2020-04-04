package week1

object JsonObject {

  abstract class JSON

  case class JSeq(elems: List[JSON]) extends JSON

  case class JObj(bindings: Map[String, JSON]) extends JSON

  case class JStr(str: String) extends JSON

  case class JNum(num: Double) extends JSON

  case class JBool(b: Boolean) extends JSON

  case object JNull extends JSON

  def show(json: JSON): String = json match {
    case JSeq(elems) =>
      "[" + (elems map show mkString ",") + "]"
    case JObj(bindings: Map[String, JSON]) =>
      val assocs = bindings map {
        case (k, v) => "\"" + k + "\":" + show(v)
      }
      "{" + (assocs mkString ", ") + "}"
    case JStr(str) => "\"" + str + "\""
    case JNum(num) => num.toString
    case JBool(b) => b.toString
    case JNull => "null"
  }

  def main(args: Array[String]): Unit = {
    val data = JObj(Map(
      "firstName" -> JStr("yogen"),
      "lastName" -> JStr("rai"),
      "married" -> JBool(true),
      "address" -> JObj(Map(
        "streed" -> JStr("Frisco"),
        "zip" -> JNum(15559)
      )),
      "phone" -> JSeq(
        List(
          JObj(Map(
            "type" -> JStr("Home"),
            "no" -> JStr("234-343-3432")
          ))
        )

      )
    ))

    println(data)

    println(show(data))

    val users =
      for {
        JObj(bindings) <- List(data)
        JSeq(phones) = bindings("phone")
        JObj(phone) <- phones
        JStr(digits) = phone("no")
        if digits startsWith "234"
      } yield (bindings("firstName"), bindings("lastName"))

    println(users)
  }
}
