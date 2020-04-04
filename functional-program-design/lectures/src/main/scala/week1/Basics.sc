
// pairs (i, j) in 2 to n where i % j == 0
val n = 10

for {
  i <- 2 to n
  j <- 2 to i
  if i % j == 0
} yield (i, j)

// similar expansion with flatMap, filter and map

(2 to n) flatMap(x => {
  (2 to x) withFilter(y => x % y == 0) map(y => (x, y))
})
