package impl

import structure.LeafNode
import structure.NestedNode
import structure.Node
import structure.Visitor
import java.util.*

/**
 * JMA - 27/04/2022 00:53
 **/
class FilterXmlTag(private val predicate: (Node) -> Boolean) : Visitor {
    // Could be a nested or leaf
    var root: Node? = null;
    // auxiliary structure to create the new document based on predicate
    private var stack: Stack<NestedNode> = Stack()
    // Auxiliary property
    val hasFound: Boolean get() = root != null

    override fun visit(n: NestedNode): Boolean {
        if (predicate.invoke(n)) {
            if (root == null) {
                root = NestedNode(n.name, mutableListOf(), n.attributes.toMutableList())
                stack.push(root as NestedNode)
            } else {
                val nestedNode = NestedNode(n.name, mutableListOf(), n.attributes.toMutableList())
                stack.peek().elements.add(nestedNode)
                stack.push(nestedNode)
            }
        } else {
            return false;
        }
        return true
    }

    override fun endVisit(n: NestedNode) {
        stack.pop()
    }

    override fun visit(l: LeafNode) {
        if (predicate.invoke(l)) {
            if (root == null) {
                root = LeafNode(l.name, l.element, l.attributes.toMutableList())
            } else {
                stack.peek().elements.add(LeafNode(l.name, l.element, l.attributes.toMutableList()))
            }
        }
    }

    /**
     * Clear context
     */
    fun clear() {
        root = null
        stack.clear()
    }

    override fun toString(): String {
        return "FilterXmlTag(root=$root)"
    }

}