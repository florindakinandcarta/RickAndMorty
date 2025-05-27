package com.example.daa

import java.util.PriorityQueue
import java.util.Stack

fun main() {
    println(numriAtomeveMeKllapa("Fe(CN)6"))

}

//Jepet nje string qe permbane vetem karakteret '({[' percaktoni nese stringu hyres eshte i vlefshem

fun aVlen(string: String): Boolean {
    val stack = ArrayDeque<Char>()
    val map = mapOf('}' to '{', ']' to '[', ')' to '(')

    for (char in string) {
        if (char in map.values) {
            stack.addLast(char)
        } else if (char in map.keys) {
            if (stack.isEmpty() || stack.removeLast() != map[char]) {
                return false
            }
        } else {
            return false
        }

    }
    return stack.isEmpty()
}


//Duke pasur parasysh nje sere intervalesh kohore takimesh qe perbehen nga oret e fillimit
// dhe mbarimit [[f1,m1],[f2,m2],...](fi<mi). Gjeni numrin minimal te sallave te konferencave te nevojshme

fun sallatTakimet(intervals: Array<IntArray>): Int {
    if (intervals.isEmpty()) return 0
    intervals.sortBy { it[0] }

    val heap = PriorityQueue<Int>()
    heap.add(intervals[0][1])

    for (i in 1 until intervals.size) {
        val start = intervals[i][0]
        val end = heap.peek()

        if (start >= end) {
            heap.poll()
        }
        heap.add(intervals[i][1])
    }
    return heap.size
}


//    4x^4 - 3x^3 - 2x^2 - 7x - 1, x = 3
fun llogaritVleren(koeficientat: List<Int>, vleraX: Int): Int {
    var rezultati = 0
    for (numer in koeficientat) {
        rezultati = rezultati * vleraX + numer
    }
    return rezultati
}

fun insertion(array: IntArray) {
    val size = array.size

    for (i in 1 until size) {
        val value = array[i]
        var j = i - 1
        while (j >= 0 && array[j] > value) {
            array[j + 1] = array[j]
            j--
        }
        array[j + 1] = value
    }
}


//fun llogaritVleren(koeficientat: List<Int>, vleraX: Int): Int {
//    var rezultati = 0
//    for (vlera in koeficientat) {
//        rezultati = rezultati * vleraX + vlera
//    }
//    return rezultati
//}


fun stringPattern(string: String): Boolean {
    val stack = ArrayDeque<Char>()
    val map = mapOf('}' to '{', ']' to '[', ')' to '(')

    for (char in string) {
        if (char in map.values) {
            stack.addLast(char)
        } else if (char in map.keys) {
            if (stack.isEmpty() || stack.removeLast() != map[char]) {
                return false
            }
        } else {
            return false
        }
    }
    return stack.isEmpty()
}

//    4x^4 - 3x^3 - 2x^2 - 7x - 1, x = 3
fun llogarite(lista: List<Int>, vleraX: Int): Int {
    var rezultati = 0
    for (vleren in lista) {
        rezultati = rezultati * vleraX + vleren
    }
    return rezultati
}

//Duke pasur parasysh nje sere intervalesh kohore takimesh qe perbehen nga oret e fillimit
// dhe mbarimit [[f1,m1],[f2,m2],...](fi<mi). Gjeni numrin minimal te sallave te konferencave te nevojshme

fun numriDhomave(oraret: Array<IntArray>): Int {
    if (oraret.isEmpty()) return 0

    oraret.sortBy { it[0] }

    val heap = PriorityQueue<Int>()

    heap.add(oraret[0][1])

    for (i in 1 until oraret.size) {
        val start = oraret[i][0]
        val end = heap.peek()

        if (start >= end) {
            heap.poll()
        }
        heap.add(oraret[i][1])
    }
    return heap.size
}

// 1 -> n^2, shuma e njejte ne rresht, kolone dhe diagonale, jo perseritje e numrave
// Shembull (katrori i rendit 3x3):
//8 1 6
//3 5 7
//4 9 2

fun generateMagicSquare(n: Int): Array<IntArray> {
    require(n % 2 != 0) { "Numri duhet te jete tek" }

    val magicSquare = Array(n) { IntArray(n) }
    var num = 1
    var row = 0
    var col = n / 2

    while (num <= n * n) {
        magicSquare[row][col] = num
        num++

        val nextRow = (row - 1 + n) % n
        val nextCol = (col + 1) % n

        if (magicSquare[nextRow][nextCol] == 0) {
            row = nextRow
            col = nextCol
        } else {
            row = (row + 1) % n
        }
    }
    return magicSquare
}


//ju jipet nje matrice katrore e rendit M e vlerave boolean qe perfaqeson
//nje tabele te fushave te lira (True) ose te zena (False).

fun madhesiaEKatrorit(tabela: Array<BooleanArray>): Int {
    val m = tabela.size
    if (m == 0) {
        return 0
    }
    val n = tabela[0].size
    if (n == 0) {
        return 0
    }

    val dp = Array(m) { IntArray(n) }
    var madhesiaMax = 0

    for (i in 0 until m) {
        for (j in 0 until n) {
            if (tabela[i][j]) { // nese fusha eshte e lire
                if (i == 0 || j == 0) {
                    dp[i][j] = 1
                } else {
                    dp[i][j] = Math.min(
                        dp[i - 1][j],
                        Math.min(dp[i][j - 1], dp[i - 1][j - 1])
                    ) + 1

                }
                madhesiaMax = Math.max(madhesiaMax, dp[i][j])
            } else {
                dp[i][j] = 0
            }
        }
    }
    return madhesiaMax
}

// formulat ne kimi
//Shembull: "H2O" dalja "H2O"
//shembull: "Mh(OH)2" Dalja: "H2MgO2"

fun numriAtomeveMeKllapa(formula: String): String {
    val counts = mutableMapOf<String, Int>()
    val numriShumzuesStiva = Stack<Int>()
    numriShumzuesStiva.push(1) // Numëruesi fillestar është 1
    var i = 0
    while (i < formula.length) {
        when (formula[i]) {
            '(' -> {
                numriShumzuesStiva.push(numriShumzuesStiva.peek())
                i++
            }
            ')' -> {
                numriShumzuesStiva.pop()
                i++
            }
            else -> {
                val element = StringBuilder()
                element.append(formula[i++])
                while (i < formula.length && formula[i].isLowerCase()) {
                    element.append(formula[i++])
                }
                val number = StringBuilder()
                while (i < formula.length && formula[i].isDigit()) {
                    number.append(formula[i++])
                }
                val count = if (number.isEmpty()) 1 else number.toString().toInt()
                val elementName = element.toString()
                counts[elementName] = (counts[elementName] ?: 0) + count * numriShumzuesStiva.peek()
            }
        }
    }

    return counts.toSortedMap().entries.joinToString("") { (element, count) ->
        element + (if (count > 1) count.toString() else "")
    }
}



