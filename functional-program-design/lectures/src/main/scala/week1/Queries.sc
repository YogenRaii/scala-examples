case class Book(title: String, authors: List[String])

val books = List(
  Book(title = "Eprogrammerz", authors = List("Yogen Rai", "Miyoz Inc")),
  Book(title = "Effective Java", authors = List("Joshu Bloch")),
  Book(title = "Effective Java 2", authors = List("Joshu Bloch")),
  Book(title = "IOE Accelerator", authors = List("Yogen Rai", "Bijay Puri")),
  Book(title = "Java Puzzler", authors = List("Joshu Bloch", "Neal Futzer")),
  Book(title = "Programming in Scala", authors = List("Martin Odersky", "Bill Venners"))
)

// title of books whose author name is Yogen
for {b <- books; a <- b.authors if a startsWith "Yogen"} yield b.title

// all books which has java in title
for {b <- books if b.title contains "Java"} yield b.title

// all author who has written at least two books

// 1. naive ways
(((books flatMap(b => b.authors map(a => (a, b)))) groupBy(_._1)) mapValues(_.length)) filter(_._2 >= 2)

// 2. query with for
{
  for {
    b1 <- books
    b2 <- books
    if b1.title < b2.title
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 == a2
  } yield a1
}.distinct
