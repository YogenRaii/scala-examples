import week4.{Signal, Var}

import scala.concurrent.Future

class BankAccount {
  val balance = Var(0)

  def deposit(amount: Int): Unit = {
    if (amount > 0) {
      val curr = balance()
      balance() = curr + amount
    }
  }

  def withDraw(amount: Int): Unit = {
    if (amount > 0 && balance() >= amount) {
      val curr = balance()
      balance() = curr - amount
    } else throw new Error("insufficient funds")
  }
}


def consolidated(accounts: List[BankAccount]): Signal[Int] =
  Signal(accounts.map(_.balance()).sum)

val a = new BankAccount()
val b = new BankAccount()

val c = consolidated(List(a, b))
c()

a deposit 100

c()

Future
