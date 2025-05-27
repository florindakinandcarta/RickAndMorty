package com.example.daa

import java.util.PriorityQueue

//fun minMeetingRooms(intervals: Array<IntArray>): Int {
//    if (intervals.isEmpty()) return 0
//
//    // Sort the intervals by start time
//    intervals.sortBy { it[0] }
//
//    // Min-heap to track the end times of meetings
//    val minHeap = PriorityQueue<Int>()
//
//    // Add the end time of the first meeting
//    minHeap.add(intervals[0][1])
//
//    for (i in 1 until intervals.size) {
//        val currentStart = intervals[i][0]
//        val earliestEnd = minHeap.peek()
//
//        // If the current meeting starts after or when the earliest ends, reuse that room
//        if (currentStart >= earliestEnd) {
//            minHeap.poll()
//        }
//
//        // Add the current meeting's end time to the heap
//        minHeap.add(intervals[i][1])
//    }
//
//    // The size of the heap is the number of rooms needed
//    return minHeap.size
//}
//fun isValid(s: String): Boolean {
//    val stack = ArrayDeque<Char>()
//    val bracketMap = mapOf(')' to '(', '}' to '{', ']' to '[')
//
//    for (char in s) {
//        if (char in bracketMap.values) {
//            stack.addLast(char)
//        } else if (char in bracketMap.keys) {
//            if (stack.isEmpty() || stack.removeLast() != bracketMap[char]) {
//                return false
//            }
//        } else {
//            return false // invalid character
//        }
//    }
//
//    return stack.isEmpty()
//}

fun main() {
//    println(isValid("()"))       // true
    println(aVlen("()[]{}"))   // true
    println(aVlen("[)"))       // false
//    println(isValid("{(]}"))
}
