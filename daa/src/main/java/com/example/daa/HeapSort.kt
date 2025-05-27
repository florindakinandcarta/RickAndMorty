package com.example.daa


//fun sort(arrayList: ArrayList<Int>) {
//    val count = arrayList.size
//    if (count == 1) {
//        return
//    }
//    buildMaxHeap(arrayList, count)
//    var endIndex = count - 1
//    while (endIndex > 0) {
//        arrayList.swap(0, endIndex)
//        siftDown(arrayList, 0, endIndex - 1)
//        endIndex--
//    }
//    return
//}


//private fun buildMaxHeap(arrayList: ArrayList<Int>, count: Int) {
//    var start = (count - 2) / 2
//    while (start >= 0) {
//        siftDown(arrayList, start, count - 1)
//        start--
//    }
//}

//fun siftDown(arrayList: ArrayList<Int>, start: Int, end: Int) {
//    var root = start
//    while (root * 2 + 1 <= end) {
//        var child = root * 2 + 1
//        if (child + 1 <= end && arrayList[child] < arrayList[child + 1]) {
//            child = root * 2 + 2
//        }
//        if (arrayList[root] < arrayList[child]) {
//            arrayList.swap(root, child)
//        } else {
//            break
//        }
//    }
//}
