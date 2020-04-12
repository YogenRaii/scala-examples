package week1

object ThreadExample {

  private val x = new AnyRef {}

  private var uid = 0L

  def getUId(): Long = x.synchronized {
    uid = uid + 1
    uid
  }


  def startThread(): Thread = {
    val t = new Thread {
      override def run() {
        val uids = for (i <- 1 to 10) yield getUId()
        println(uids)
      }
    }

    t.start()

    t
  }

  def main(args: Array[String]): Unit = {

    startThread()
    startThread()
  }

}
