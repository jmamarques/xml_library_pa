package impl

import structure.LeafNode
import structure.NestedNode
import structure.Node
import structure.Visitor

/**
 * JMA - 27/04/2022 00:53
 **/
class FindXmlTag(private val predicate: (Node) -> Boolean) : Visitor {

    /* Node founded base on #predicate */
    var node: Node? = null

    /* Calculated property - Has found node */
    val hasFound: Boolean get() = node != null

    override fun visit(n: NestedNode): Boolean {
        // match predicate and has not found a node
        if (!hasFound && predicate.invoke(n)) {
            node = n
            // false to stop the search on nested node
            return false
        }
        return true
    }

    override fun visit(l: LeafNode) {
        if (!hasFound && predicate.invoke(l)) {
            node = l
        }
    }

    /**
     * Clear Visitor finder - reuse object
     */
    fun clear() {
        node = null
    }

    override fun toString(): String {
        return "FindXmlTag(node=$node, hasFound=$hasFound)"
    }

}
