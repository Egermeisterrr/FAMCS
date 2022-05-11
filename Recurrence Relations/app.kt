import java.io.*
import java.util.*
import kotlin.math.max
import kotlin.math.min

class Ward(var capacity: Int, private var countOfIll: Int, var number: Int) {
    val freePlace: Int
        get() = capacity - countOfIll
    val room: Ward
        get() = Ward(capacity, countOfIll, number)
}

fun main() {
    val inp = Scanner(File("input.txt"))
    val out = PrintStream(FileOutputStream("output.txt"))

    var aCount = inp.nextInt()
    var bCount = inp.nextInt()
    val nCount = inp.nextInt()

    val ca = aCount
    val cb = bCount

    var fPerson = 0
    var count = 1
    var s = ""
    var res1 = 0

    val aIll = ArrayList<Ward>()
    val freeRoom = ArrayList<Ward>()

    while (inp.hasNextInt()) {
        val ni = inp.nextInt()
        val ai = inp.nextInt()
        val bi = inp.nextInt()

        if (ai != 0 && bi == 0 && aCount > 0 && ni != 0) {
            aIll.add(Ward(ni, ai, count))
            aCount -= ni - ai
            res1 += ni - ai
        }
        if (bi != 0 && ai == 0 && bCount > 0 && ni != 0) {
            bCount -= ni - bi
            res1 += ni - bi
        }
        if (ai == 0 && bi == 0 && ni != 0) {
            freeRoom.add(Ward(ni, ai, count))
            fPerson += ni
        }
        count++
    }

    var res2 = res1
    freeRoom.sortWith(Comparator { o1, o2 ->
        if (o1.freePlace == o2.freePlace)
            0
        else if (o1.freePlace > o2.freePlace)
            1
        else
            -1
    })

    if (aCount <= 0 && bCount <= 0) {
        out.println(ca + cb)
        for (j in aIll.indices)
            out.print(" " + aIll[j].number)
    }
    else if (aCount <= 0 && bCount > 0) {
        if (bCount > fPerson) {
            s = ((ca + cb - (bCount - fPerson)).toString())
            out.print(s)
        }
        if (bCount < fPerson) {
            val k = ca + cb
            out.println(k)
            for (j in aIll.indices)
                out.print(" " + aIll[j].number)
        }
    }
    else if (bCount <= 0 && aCount > 0) {
        if (aCount > fPerson) {
            s = (ca + cb - (bCount - fPerson)).toString()
            out.print(s)
        }
        if (aCount < fPerson) {
            var q = 0
            while (aCount > 0) {
                aIll.add(freeRoom[q].room)
                aCount -= freeRoom[q].capacity
                q++
            }
            val k = ca + cb
            aIll.sortWith(Comparator { o1, o2 ->
                if (o1.number == o2.number)
                    0
                else if (o1.number > o2.number)
                    1
                else
                    -1
            })
            out.println(k)
            for (j in aIll.indices)
                out.print(" " + aIll[j].number)
        }
    }
    else {
        val mas = IntArray(fPerson + 1)
        for (p in 0 until fPerson + 1)
            mas[p] = 0
        mas[0] = 1
        val matrix = Array(fPerson + 1) {
            IntArray(freeRoom.size)
        }
        for (i in 0 until fPerson + 1) {
            for (j in freeRoom.indices)
                matrix[i][j] = 0
            if (i == fPerson) {
                mas[i] = 1
                for (j in freeRoom.indices)
                    matrix[i][j] = freeRoom[j].number
            }
        }
        var lastI = 1
        for (k in freeRoom.indices) {
            var temp = 0
            for (t in lastI downTo 1) {
                if (mas[t] == 1 && mas[t + freeRoom[k].capacity] == 0) {
                    mas[t + freeRoom[k].capacity] = 1
                    temp = t + freeRoom[k].capacity
                    matrix[t + freeRoom[k].capacity][0] = freeRoom[k].number
                    var w = 0
                    while (matrix[t][w] != 0) {
                        matrix[t + freeRoom[k].capacity][w + 1] = matrix[t][w]
                        w++
                    }
                }
            }
            if (mas[freeRoom[k].capacity] == 0) {
                mas[freeRoom[k].capacity] = 1
                matrix[freeRoom[k].capacity][0] = freeRoom[k].number

                if (temp < freeRoom[k].capacity)
                    temp = freeRoom[k].capacity
            }
            lastI = temp + 1
        }
        var flag = false
        var p: Int
        p = aCount
        while (p < fPerson + 1) {
            if (mas[p] == 1) {
                flag = true
                break
            }
            p++
        }
        if (flag && bCount <= fPerson - p) {
            var w = 0
            while (matrix[p][w] != 0) {
                aIll.add(Ward(0, 0, matrix[p][w]))
                w++
            }
            aIll.sortWith(Comparator { o1, o2 ->
                if (o1.number == o2.number)
                    0
                else if (o1.number > o2.number)
                    1
                else
                    -1
            })
            val k = ca + cb
            out.println(k)
            for (j in aIll.indices) {
                s += aIll[j].number.toString() + " "
            }
            out.print(s.trim { it <= ' ' })
        }
        else {
            p = aCount
            while (p > -1) {
                if (mas[p] == 1) {
                    break
                }
                p--
            }
            res1 += p + min(bCount, fPerson - p)
            p = bCount
            while (p > -1) {
                if (mas[p] == 1) {
                    break
                }
                p--
            }
            res2 += p + min(aCount, fPerson - p)
            s = (max(res1, res2)).toString()
            out.print(s)
        }
    }
    out.flush()
}