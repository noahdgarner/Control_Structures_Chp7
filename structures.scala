import scala.io.StdIn._
import java.io.{FileNotFoundException, IOException, FileReader}
import java.net.{MalformedURLException, URL}
//if expressions, here is an imperative if statement, declares a var, filename,
//and initializes it to a default value
var filename = "default.txt"
if (!args.isEmpty)
  filename = args(0)
//achieves same thing, because in scala, IF is an expression that results in a value
// here it is without using vars
//the advantage here is it uses a val instead of a var, telling readers the variable
//will never change, saving them from scanning all code in variables scope to find a change
val filename2 = if (!args.isEmpty) args(0) else "default"
//ADVANTAGE 2 OF VAL INSTEAD OF VAR: it supports equational reasoning
//This means the introduced variable is eqaul to the expressions that computes it, assuming there are no side effects
//so instead of writing println(filename), you can write println(if (!args.isEmpty) args(0) else "default.txt")
println(filename2)
//same thing because of equational reasoning
println(if (!args.isEmpty) args(0) else "default.txt")


//scala while loop - imperative
def gcdImperative(x: Long, y: Long): Long = {
  var a = x
  var b = y
  while (a != 0) {
    val temp = a
    a = b % a
    b = temp
  }
  b
}
//here is the functional, recursive definition, no variables, no while loop
//just liek avoiding the use of vars, while loop goes hand in hand. Try to avoid them with recursion
def gcdFunctional(x: Long, y: Long): Long =
  if (y == 0) x else gcdFunctional(y, x% y)

//scala do-while: echo lines read from standard input until an empty line is entered
/*var line = ""
do {
  line = readLine()
  println("Read: "+ line)
} while (line != "")*/

//FOR EXPRESSIONS!!!
// THIS METHOD RETURNS AN ARRAY OF fILE OBJECTS, ONE object PER DIRECTY & FILE CONTAINED
//in the current directory. We store the resulting array in the filesHere variable
val filesHere = (new java.io.File(".")).listFiles
//this next exp is called a generator, it iterates through the elements of filesHere
//in each iteration, a new val named file is initialized with an element value!!! OHHH
//compiler infers type of file to be File type, because filesHere is an Array[File]
for (file <- filesHere)
  println(file)

//also can create a range and iterate with for
for (i <- 1 to 4)
  println("Iteration: " + i)
//use until if you don't want to include upper bound (for arrays)
for (i <- 1 until 4)
  println("Iteration: " + i)

//the code showin lists only files in the current directory whose names end with ".scala"
val filesHere2 = (new java.io.File(".")).listFiles
//we can keep adding more filters by adding if clauses to <- right
//example of more robust version of the above
//note: must use semi colons for multiple filters in for expression
for (file <- filesHere2 if file.isFile; if file.getName.endsWith(".scala"))
  println(file)

//nested iteration, adding multiple <- clauses yields nested "loops"
//e.g. (exemplia gratia) following has two nested loops
//the outer iterates through filesHere, the inner iterates through fileLines(file)
//for any file ending with .scala
def fileLines(file: java.io.File) =
  scala.io.Source.fromFile(file).getLines.toList

def grep(pattern: String) =
  for {
    file <- filesHere
    if file.getName.endsWith(".scala")
    line <- fileLines(file)
    trimmed = line.trim
    if trimmed.matches(pattern)
  } println(file +": "+ trimmed)

grep(".*gcd.*")

//this will not work. yield file is a part of the body in this case, so wrong
/*for (file <- filesHere if file.getName.endsWith(".scala")) {
    yield file  // Syntax error!
  }*/

//yield is used to generate a value to remember each iteration
//notice no prantheses, this is to avoid use of semi colons
val forLineLengths =
  for {
    file <- filesHere
    if file.getName.endsWith(".scala"); if file.isFile()
    line <- fileLines(file)
    trimmed = line.trim //eliminate white space
    if trimmed.matches(".*for.*")
    //this yield puts a value into forLineLengths Array
  } yield trimmed.length //if this was not trimmed, it would include length with white space

//prints array address
println(forLineLengths)
//prints second element
println(forLineLengths(1))

//Exception Handling with try expressions
//instead of returning a value, a method can terminate by throwing an exception
//method's caller can either catch and handle the exception, or it can
//itself simply terminate, in which case the exception propagtes in this way
//until a method handles it or theres no more methods left
//THROWING EXCEPTIONS
//throw new IllegalArgumentException
//here is an example of a throw with a result type that matters
//notice 1 branch computes a value, and the else branch computes nothing, exception throws are nothing
/*val n = 9
val half =
  if (n % 2 == 0)
    n/2
  else
  //throw exceptions on when writing reusable libraries
    throw new RuntimeException("n must be even")*/

//catching exceptions
try {
  val file = new FileReader("input.txt")
  //use an close file
}
  //syntax for catch clauses was chosen for its consistency with pattern matching (powerful feature)
catch {
  //ex: exception
  case ex: FileNotFoundException => //handle missing file
  case ex: IOException => //handle other I/O error
}

//example of a finally clause, this wil lalways be xecuted. use to check if file closed
//finally clause should not change any values, just do clean up
/*val file = new FileReader("input")
try {
  //use an close file
}
finally{
  file.close()
}*/

//here is an example of a catch clause that yields a value:
def urlFor(path: String) =
  try{
    new URL(path) //
  }
  catch {
    case e: MalformedURLException =>
      new URL("http://www.scala-lang.org")
  }
//this will cause the case to act since this URL is invalid
urlFor("httpom/noahdgarner/intellij_scala_controlstructs/tree/master")


//MATCH EXPRESSIONS: similar to switch statements
//in general, a match expression lets you select using arbitrary patterns ( more on this later)
val firstArg = if (args.length > 0) args(0) else ""
//notice there are no breaks, it is implicit
firstArg match {
  case "salt" => println("pepper")
  case "chips" => println("salsa")
  case "eggs" => println("bacon")
    //placeholder for completely uknown value
  case _ => println("huh?")
}

//here is a transliterated while loop from java into scala
// notice it has no continue
var i = 0
var foundIt = false

//finds thefirst string that does not start with - and ends with .scala, nice and sweet/concise
while (i < args.length && !foundIt) {
  if (!args(i).startsWith("-"))
    if (args(i).endsWith(".scala"))
      foundIt = true
  i+=1
}

//this approach will use recursion to eliminate the while, and the if statement
def searchFrom(i: Int): Int = {
  if (i >= args.length) -1
  else if (args(i).startsWith("-")) searchFrom(i + 1)
  else if (args(i).endsWith(".scala")) i
  else searchFrom(i + 1)
}
val result = searchFrom(0)
println(result)

//SCOPE WITH A MULTIPLICATION FUNCTION
def printMultiTable() {
  var i = 1
  // only i in scope here
  while (i <= 10) {
    var j = 1
    // both i and j in scope here
    while (j <= 10) {
      //toString so we can call.toString on (i*j) to find prod.length
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

//here is a refactored version of the multiply table as a functional style
//returns a row as a sequence
// Returns a row as a sequence
def makeRowSeq(row: Int) =
  for (col <- 1 to 10) yield {
    val prod = (row * col).toString
    val padding = " " * (4 - prod.length)
    padding + prod
  }
// Returns a row as a string
def makeRow(row: Int) = makeRowSeq(row).mkString
// Returns table as a string with one row per line
def multiTable() = {
  //return a sequence of row strings, so tableseq look like string 1:"___1___2___3..." string 2:"___2___4___6..." etc
  val tableSeq = for (row <- 1 to 10) yield makeRow(row)
  //this puts an end of line character between each string
  println(tableSeq(9))
  tableSeq.mkString("\n")
}

println(multiTable())


