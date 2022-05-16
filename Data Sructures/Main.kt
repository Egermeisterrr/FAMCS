import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*

class Vertex(var x: Int, var y: Int, var mark: Long, var height: Int)

fun solution() : Int {
   // code for calculating the minimum path
   return 0
}

fun main() {
   val inp = Scanner(File("in.txt"))
   val out = PrintStream(FileOutputStream("out.txt"))

   val n = inp.nextInt()
   val m = inp.nextInt()
   val matrix = Array(n) { Array(m) { 0 } }

   for(i in 0 until n) {
      for(j in 0 until m) {
         val num = inp.nextInt()
         matrix[i][j] = num
      }
   }

   val k = inp.nextInt()
   val xStart = inp.nextInt()
   val yStart = inp.nextInt()
   val xEnd = inp.nextInt()
   val yEnd = inp.nextInt()

   val answer = solution()

   out.println(answer)
}