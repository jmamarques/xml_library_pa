package structure

/**
 * JMA - 18/04/2022 23:54
 **/
class NestedNode(name: String, val elements: MutableList<Node>, attributes: MutableList<Attribute>): Node(name, attributes){
    override fun accept(v: Visitor) {
        if(v.visit(this))
            elements.forEach {
                it.accept(v)
            }
        v.endVisit(this)
    }

}
