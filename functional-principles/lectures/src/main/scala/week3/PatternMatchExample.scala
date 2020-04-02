package week3

object PatternMatchExample {
  
  def main(args: Array[String]): Unit = {
    println(matchTest(3))
    
    //more examples
    val yogen = new Person("Yogen",24)
    val usha = new Person("Usha", 21)
    
    for(person <- List(yogen, usha, new Person("Name", 100))) {
      person match {
        case Person("Usha", 21) => println("Hello Usha") 
        case Person("Yogen", 24) => println("Hello Yogen")
        case _ => println("Hi, how are you??")
      }
    }
  }
  
  def matchTest(x: Int): String = x match {
    case 1 => "One"
    case 2 => "Two"
    case _ => "Many"
  }
  
  case class Person(name: String, age: Int)
}
