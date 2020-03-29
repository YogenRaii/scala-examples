type Word = String

type Sentence = List[Word]

type Occurrences = List[(Char,Int)]

def wordOccurrences(w: Word) =
  w.toLowerCase.groupBy((element:Char) => element).toList map({
    case (c,l) => (c, l.length)
  }) sorted

wordOccurrences("Yogeeny")

def sentenceOccurrences(s: Sentence): Occurrences =
  wordOccurrences(s mkString )


sentenceOccurrences(List("I","amm","amm","yogen"))

var res = ""
List("I","am") foreach (e => res += e)
println(res)
List("I","amm","amm") flatMap(w => wordOccurrences(w))

List("I","amm","amm") mkString