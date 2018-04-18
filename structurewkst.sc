//this is a worksheet
//pushign to git

val x = 1;

//Exception Handling with try expressions
//instead of returning a value, a method can terminate by throwing an exception
//method's caller can either catch and handle the exception, or it can
//itself simply terminate, in which case the exception propagtes in this way
//until a method handles it or theres no more methods left
//THROWING EXCEPTIONS
//throw new IllegalArgumentException
//here is an example of a throw with a result type that matters

var n = 10
val half =
  if (n % 2 == 0)
    n/2
  else
    throw new RuntimeException("n must be even")
//and if n is odd, exception thrown at runtime


def printMultiTable() {
  var i = 1
  // only i in scope here
  while (i <= 10) {
    var j = 1
    // both i and j in scope here
    while (j <= 10) {
      val prod = (i * j).toString
      // i, j, and prod in scope here
      var k = prod.length
      // i, j, prod, and k in scope here
      while (k < 4) {
        print(" ")
        k += 1
      }
      print(prod)
      j += 1
    }
    // i and j still in scope; prod and k out of scope
    println()
    i += 1
  }
  // i still in scope; j, prod, and k out of scope
}


printMultiTable()
