
class TreeNode(_value: Int = 0, _left: TreeNode = null, _right: TreeNode = null) {
  var value: Int = _value
  var left: TreeNode = _left
  var right: TreeNode = _right
}

object Solution {
  def isSameTree(p: TreeNode, q: TreeNode): Boolean = {
    (p, q) match {
      case (p.left.value == q.left.value && p.right.value == q.right.value) => {
        isSameTree(p.left, q.left)
        isSameTree(p.right, q.right)
      }
      case _ => false
    }
  }
}