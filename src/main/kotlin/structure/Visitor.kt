package structure

/**
 * JMA - 19/04/2022 01:29
 **/
interface Visitor {
    fun visit(n: NestedNode)
    fun endVisit(n: NestedNode)
    fun visit(l: LeafNode)
}
