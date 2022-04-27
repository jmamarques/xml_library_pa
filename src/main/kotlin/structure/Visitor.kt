package structure

/**
 * JMA - 19/04/2022 01:29
 **/
interface Visitor {
    /**
     * Visit Nested Node
     */
    fun visit(n: NestedNode): Boolean = true
    /**
     * End Visit in Nested Node
     */
    fun endVisit(n: NestedNode) {}
    /**
     * Visit a LeafNode
     */
    fun visit(l: LeafNode) {}
}
