fun maximumGap(nums: IntArray): Int {
    // If the array has fewer than two elements, return 0
    if (nums.size <= 2) return 0

    // Perform radix sort
    radixSort(nums)

    // Compute the maximum gap between consecutive elements
    var maxGap = 0
    for (i in 1 until nums.size) {
        maxGap = maxOf(maxGap, nums[i] - nums[i - 1])
    }
    return maxGap
}

fun radixSort(arr: IntArray) {
    val max = arr.maxOrNull() ?: return
    var exp = 1

    val output = IntArray(arr.size)

    while (max / exp > 0) {
        val count = IntArray(10)

        // Count occurrences of digits
        for (i in arr.indices) {
            count[(arr[i] / exp) % 10]++
        }

        // Accumulate count
        for (i in 1 until 10) {
            count[i] += count[i - 1]
        }

        // Build the output array
        for (i in arr.indices.reversed()) {
            val digit = (arr[i] / exp) % 10
            output[--count[digit]] = arr[i]
        }

        // Copy output to arr
        for (i in arr.indices) {
            arr[i] = output[i]
        }

        exp *= 10
    }
}

// Example usage
fun main() {
    val array = intArrayOf(9, 18, 27, 3)
    println(maximumGap(array)) // Output: 9

    // Edge case with fewer than two elements
    val smallArray = intArrayOf(9,1)
    println(maximumGap(smallArray)) // Output: 0
}
