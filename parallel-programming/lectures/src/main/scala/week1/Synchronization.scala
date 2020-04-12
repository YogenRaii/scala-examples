package week1

object Synchronization {
  private val x = new AnyRef {}

  private var uid = 0L

  def getUId(): Long = x.synchronized {
    uid = uid + 1
    uid
  }


  class BankAccount(var amount: Int = 0) {

    private def lockAndTransfer(target: BankAccount, x: Int): Unit = {
      this.synchronized {
        target.synchronized {
          this.amount -= x
          target.amount += x
        }
      }
    }

    val uid = getUId()

    def transfer(target: BankAccount, x: Int): Unit = {
      if (this.uid < target.uid) this.lockAndTransfer(target, x)
      else target.lockAndTransfer(this, -x)
    }
  }


  def startThread(a: BankAccount, b: BankAccount, n: Int): Thread = {
    val t = new Thread {
      override def run(): Unit = {
        for (i <- 1 to n) a.transfer(b, 1)
      }
    }

    t.start()

    t
  }

  def main(args: Array[String]): Unit = {
    val a1 = new BankAccount(100)
    val a2 = new BankAccount(200)

    val t = startThread(a1, a2, 10)
    val s = startThread(a2, a1, 20)

    t.join() // join t to main thread
    s.join() // join s to main thread

    println(a1.amount) // 110
    println(a2.amount) // 190
  }
}
