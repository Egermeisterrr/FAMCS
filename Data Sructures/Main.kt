import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*
import kotlin.math.abs

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
    val pq: PriorityQueue<Vertex> = PriorityQueue<Vertex>(n * m, comparator)
    var j: Int
    var u: Int
    val routes = Array(n + 2) { IntArray(m + 2) }
    val visited = Array(n + 2) { BooleanArray(m + 2) }
    for (i in 0 until n + 2) {
        for (l in 0 until m + 2) {
            routes[i][l] = Int.MAX_VALUE
        }
    }
    for (i in 0 until n + 2) {
        visited[i][0] = true
        visited[i][m + 1] = true
    }
    for (i in 0 until m + 1) {
        visited[0][i] = true
        visited[n + 1][i] = true
    }
    var buf: Vertex
    pq.add(Vertex(xStart, yStart, 0, matrix[xStart][yStart]))
    visited[xStart][yStart] = true
    while (!pq.isEmpty()) {
        buf = pq.poll()
        j = buf.x
        u = buf.y
        routes[j][u] = buf.mark
        visited[j][u] = true
        if (visited[xEnd][yEnd]) {
            break
        }
        if (!visited[j + 1][u] && abs(matrix[j][u] - matrix[j + 1][u]) + k + routes[j][u] < routes[j + 1][u]) {
            pq.add(
                Vertex(
                    j + 1, u, abs(matrix[j][u] - matrix[j + 1][u]) + k + routes[j][u],
                    matrix[j + 1][u]
                )
            )
            routes[j + 1][u] = abs(matrix[j][u] - matrix[j + 1][u]) + k + routes[j][u]
        }
        if (!visited[j - 1][u] && abs(matrix[j][u] - matrix[j - 1][u]) + k + routes[j][u] < routes[j - 1][u]) {
            pq.add(
                Vertex(
                    j - 1, u, abs(matrix[j][u] - matrix[j - 1][u]) + k + routes[j][u],
                    matrix[j - 1][u]
                )
            )
            routes[j - 1][u] = abs(matrix[j][u] - matrix[j - 1][u]) + k + routes[j][u]
        }
        if (!visited[j][u + 1] && abs(matrix[j][u] - matrix[j][u + 1]) + k + routes[j][u] < routes[j][u + 1]) {
            pq.add(
                Vertex(
                    j, u + 1, abs(matrix[j][u] - matrix[j][u + 1]) + k + routes[j][u],
                    matrix[j][u + 1]
                )
            )
            routes[j][u + 1] = abs(matrix[j][u] - matrix[j][u + 1]) + k + routes[j][u]
        }
        if (!visited[j][u - 1] && abs(matrix[j][u] - matrix[j][u - 1]) + k + routes[j][u] < routes[j][u - 1]) {
            pq.add(
                Vertex(
                    j, u - 1, abs(matrix[j][u] - matrix[j][u - 1]) + k + routes[j][u],
                    matrix[j][u - 1]
                )
            )
            routes[j][u - 1] = abs(matrix[j][u] - matrix[j][u - 1]) + k + routes[j][u]
        }
    }
    return routes[xEnd][yEnd]
}

fun main() {
    val inp = Scanner(File("in.txt"))
    val out = PrintStream(FileOutputStream("out.txt"))

    val n = inp.nextInt()
    val m = inp.nextInt()
    val matrix = Array(n + 2) { Array(m + 2) { 0 } }

    for (i in 1 .. n) {
        for (j in 1 .. m) {
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
