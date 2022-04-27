package impl

import structure.LeafNode
import structure.NestedNode
import structure.Node
import structure.Visitor

/**
 * JMA - 27/04/2022 00:53
 **/
class FindMultipleXmlTag(private val predicate: (Node) -> Boolean) : Visitor {

    /* Node founded base on #predicate */
    var nodes: MutableList<Node> = mutableListOf()

    /* Calculated property - Has found node */
    val hasFound: Boolean get() = nodes.isNotEmpty()

    override fun visit(n: NestedNode): Boolean {
        // match predicate and has not found a node
        if (predicate.invoke(n)) {
            nodes.add(n)
        }
        return true
    }

    override fun visit(l: LeafNode) {
        if (predicate.invoke(l)) {
            nodes.add(l)
        }
    }

    /**
     * Clear Visitor finder - reuse object
     */
    fun clear() {
        nodes.clear()
    }

    override fun toString(): String {
        return "FindXmlTag(node=$nodes, hasFound=$hasFound)"
    }

}
