import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*

class Vertex(var x: Int, var y: Int, var mark: Int, var height: Int)

fun solution(
    n: Int,
    m: Int,
    matrix: Array<Array<Int>>,
    xStart: Int,
    yStart: Int,
    xEnd: Int,
    yEnd: Int,
    k: Int
): Int {
    val comparator: Comparator<Vertex> = Comparator<Vertex> { o1, o2 ->
       if (o1.mark < o2.mark) {
           return@Comparator -1
       }
       else if (o1.mark > o2.mark) {
          return@Comparator 1
       }
       else {
           return@Comparator 0
       }
    }

     return 0
}

fun main() {
    val inp = Scanner(File("in.txt"))
    val out = PrintStream(FileOutputStream("out.txt"))

    val n = inp.nextInt()
    val m = inp.nextInt()
    val matrix = Array(n) { Array(m) { 0 } }

    for (i in 0 until n) {
        for (j in 0 until m) {
            val num = inp.nextInt()
            matrix[i][j] = num
        }
    }

    val k = inp.nextInt()
    val xStart = inp.nextInt()
    val yStart = inp.nextInt()
    val xEnd = inp.nextInt()
    val yEnd = inp.nextInt()

    val answer = solution(n, m, matrix, xStart, yStart, xEnd, yEnd, k)

    out.println(answer)
}
