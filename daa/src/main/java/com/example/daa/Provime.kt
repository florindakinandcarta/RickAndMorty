package com.example.daa

import radixSort
import java.util.PriorityQueue


//TODO() JANAR 2025 ===============
//Jepet nje string qe permbane vetem karakteret '({[' percaktoni nese stringu hyres eshte i vlefshem

fun isValid(s: String): Boolean {
    val stack = ArrayDeque<Char>()
    val bracketMap = mapOf(')' to '(', '}' to '{', ']' to '[')


    for (char in s) {
        if (char in bracketMap.values) {
            stack.addLast(char)
        } else if (char in bracketMap.keys) {

            if (stack.isEmpty() || stack.removeLast() != bracketMap[char]) {
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

fun minMeetingRooms(intervals: Array<IntArray>): Int {
    if (intervals.isEmpty()) return 0

    intervals.sortBy { it[0] }

    val minHeap = PriorityQueue<Int>()

    minHeap.add(intervals[0][1])

    for (i in 1 until intervals.size) {
        val currentStart = intervals[i][0]

        val earliestEnd = minHeap.peek() // na jep elementin e pare ne heap

        if (currentStart >= earliestEnd) {
            minHeap.poll() // heq elementin e pare
        }

        minHeap.add(intervals[i][1])
    }
    return minHeap.size
}

//    4x^4 - 3x^3 - 2x^2 - 7x - 1, x = 3
fun llogaritn(koeficientat: List<Int>, vleraX: Int): Int {
    var rezultati = 0
    for (vlera in koeficientat) {
        rezultati = rezultati * vleraX + vlera
    }
    return rezultati
}


// TODO() JANAR 2025 ===============






//Duke pasur parasysh nje numrave te plote, ktheni differencen maksimale mes dy elementeve te njepasneshme
//nese lista permbane me pak se dy elemente ktheni 0

fun maximumGap(nums: IntArray): Int {
    if (nums.size <= 2) return 0

    radixSort(nums)

    var maxGap = 0

    for (i in 1 until nums.size) {
        maxGap = maxOf(maxGap, nums[i] - nums[i - 1])
    }

    return maxGap
}

//Implementoni selection sort ne kod

fun selectionSort(array: IntArray) {
    val size = array.size

    for (i in 0 until size - 1) {
        var minIndex = i

        for (j in i + 1 until size) {

            if (array[j] < array[minIndex]) {
                minIndex = j
            }
        }

        val temp = array[minIndex]
        array[minIndex] = array[i]
        array[i] = temp
    }
}


//Insertion sort

fun insertionSort(array: IntArray) {
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


// sequential search
fun sequentialSearch(arr: IntArray, size: Int, target: Int): Boolean {
    for (i in 0 until size) {
        if (target == arr[i]) {
            return true // Target found
        }
    }
    return false // Target not found
}




//Ju jepet nje matrice e vlerave MxN qe perfaqeson nje tabele fushash te lira(True) ose te zena(False).
//Gjeni madhesine e katrorit me te madh te fushave te lira

fun largestFreeSquare(matrix: Array<Array<Boolean>>): Int {

    if (matrix.isEmpty() || matrix[0].isEmpty()) return 0

    val rows = matrix.size
    val columns = matrix[0].size

    val tabela = Array(rows) { IntArray(columns) }

    var maxSize = 0

    for (i in 0 until rows) {
        for (j in 0 until columns) {

            if (matrix[i][j]) {

                if (i == 0 || j == 0) {
                    tabela[i][j] = 1   // kolona e pare, rreshti pare e vendosim nje sepse e vetem nje qelule eshte tashme
                } else {
                    tabela[i][j] = 1 + minOf(
                        tabela[i - 1][j],   // top
                        tabela[i][j - 1],  // left
                        tabela[i - 1][j - 1]  //top-left
                    )
                }

                maxSize = maxOf(maxSize, tabela[i][j])
            }
        }
    }
    return maxSize
}









