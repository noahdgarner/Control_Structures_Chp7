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
def gcdLoop(x: Long, y: Long): Long = {
  var a = x
  var b = y
  while (a != 0) {
    val temp = a
    a = b % a
    b = temp
  }
  b
}
//scala do-while: echo lines read from standard input until an empty line is entered
var line = ""
do {
  line = readLine()
  println("Read: "+ line)
} while (line != "")











