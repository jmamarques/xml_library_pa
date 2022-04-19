package structure

/**
 * JMA - 18/04/2022 23:54
 **/
class NestedNode(name: String, val elements: List<Node>, attributes: List<Attribute>): Node(name, attributes){
    override fun accept(v: Visitor) {
        TODO("Not yet implemented")
    }

}
