package com.example.rickandmorty

fun main() {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val target = 4
    val array = intArrayOf(6, 2, 4, 7, 1, 3, 8, 5)
    val numbers = mutableListOf(310, 213, 23, 130, 13, 301, 222, 32, 201, 111, 323, 2, 330, 102, 231, 120)
    val res = radixSort(numbers)
    res.forEach {
        println(it)
    }
}

fun sequentialSearch(list: List<Int>, target: Int): Int {
    for (i in 0 until list.size) {
        if (list[i] == target) {
            return i
        }
    }
    return -1
}

fun binarySearch(list: List<Int>, target: Int): Int {
    var first = 0
    var last = list.size
    while (first < last) {
        val midIndex = (first + last) / 2
        val midElement = list[midIndex]
        when {
            target < midElement -> last = midIndex
            target > midElement -> first = midIndex + 1
            else -> return midIndex
        }
    }
    return -1
}

fun insertionSort(array: IntArray): IntArray {
    for (i in 1 until array.size) {
        val key = array[i]
        var j = i - 1
        while (j >= 0 && array[j] > key) {
            array[j + 1] = array[j]
            j--
        }
        array[j + 1] = key
    }
    return array
}


fun bubbleSort(array: IntArray): IntArray {
    val size = array.size
    for (i in 0 until size - 1) {
        for (j in 0 until size - i - 1) {
            if (array[j] > array[j + 1]) {
                val temp = array[j]
                array[j] = array[j + 1]
                array[j + 1] = temp
            }
        }
    }
    return array
}

fun shellSort(array: IntArray): IntArray {
    val size = array.size
    var gap = size / 2
    while (gap > 0) {
        for (i in gap until size) {
            val temp = array[i]
            var j = i
            while (j >= gap && array[j - gap] > temp) {
                array[j] = array[j - gap]
                j -= gap
            }
            array[j] = temp
        }
        gap /= 2
    }
    return array
}

fun radixSort(mutableList: MutableList<Int>): MutableList<Int> {
    val base = 10
    var done = false
    var digits = 1
    while (!done) {
        done = true
        val buckets = arrayListOf<MutableList<Int>>().apply {
            for (i in 0..9) {
                add(arrayListOf())
            }
        }
        mutableList.forEach { number ->
            val remainingPart = number / digits
            val digit = remainingPart % base
            buckets[digit].add(number)
            if (remainingPart > 0) {
                done = false
            }
        }
        digits *= base
        mutableList.clear()
        mutableList.addAll(buckets.flatten())
    }
    return mutableList
}