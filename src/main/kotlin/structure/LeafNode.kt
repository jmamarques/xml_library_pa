package structure

/**
 * JMA - 18/04/2022 23:54
 **/
class LeafNode(name: String, val element: String, attributes: MutableList<Attribute>): Node(name, attributes){
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}
