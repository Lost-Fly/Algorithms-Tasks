import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Solution {
  def gcd(a: Int, b: Int): Boolean = {
    if (a == 0) return b != 1
    if (b == 0) return a != 1

    var aa = a
    var bb = b

    var az = Integer.numberOfTrailingZeros(aa)
    var bz = Integer.numberOfTrailingZeros(bb)
    val shift = Math.min(az, bz)

    bb >>= bz

    while (aa != 0) {
      aa >>= az
      val diff = bb - aa
      az = Integer.numberOfTrailingZeros(diff)
      bb = Math.min(aa, bb)
      aa = Math.abs(diff)
    }

    (bb << shift) != 1
  }

  def dfs(v: Int, num: Int, adjMatrix: Array[ListBuffer[Int]], component: Array[Int]): Unit = {
    val queue = mutable.Queue(v)
    component(v) = num

    while (queue.nonEmpty) {
      val current = queue.dequeue()

      for (neighbor <- adjMatrix(current)) {
        if (component(neighbor) == 0) {
          queue.enqueue(neighbor)
          component(neighbor) = num
        }
      }
    }
  }


  def areAllEqual(nums: Array[Int]): Boolean = {
    if (nums.isEmpty) {
      true
    } else {
      val first = nums.head
      nums.tail.forall(num => num == first && num != 1)
    }
  }

  def canTraverseAllPairs(nums: Array[Int]): Boolean = {
    if (areAllEqual(nums)) return true
    val numLength: Int = nums.length

    val graph = Array.fill(numLength)(collection.mutable.ListBuffer[Int]())


    for {
      i <- 0 until numLength
      j <- (i + 1) until numLength
      if gcd(nums(i), nums(j))
    } {
      graph(i) += j
    }

    val component_num: Int = 1
    val component = Array.fill(numLength)(0)

    dfs(0, component_num, graph, component)

    component.forall(_ == 1)
  }
}
