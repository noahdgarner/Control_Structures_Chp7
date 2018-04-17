import scala.io.StdIn._
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
for (file <- filesHere2 if file.getName.endsWith(".scala"))
  println(file)
//we can keep adding more filters by adding if clauses to <- right
//example of more robust version of the above
//note: must use semi colons for multiple filters in for expression
for (file <- filesHere2 if file.isFile; if file.getName.endsWith(".scala"))
  println(file)














