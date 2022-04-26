package impl

import structure.LeafNode
import structure.NestedNode
import structure.Node
import structure.Visitor

/**
 * JMA - 25/04/2022 22:42
 **/
class XmlToString: Visitor {
    private val xmlBuilder: StringBuilder = StringBuilder()

    override fun visit(n: NestedNode): Boolean {
        xmlBuilder.append("<${n.name}${this.attributesToString(n)}>\n")
        return true
    }

    override fun endVisit(n: NestedNode) {
        xmlBuilder.append("</${n.name}>\n")
    }

    override fun visit(l: LeafNode) {
        xmlBuilder.append("<${l.name}${this.attributesToString(l)}>${l.element}</${l.name}>\n")
    }

    fun clear() {
        xmlBuilder.clear()
    }

    override fun toString(): String {
        return xmlBuilder.toString()
    }

    private fun attributesToString(n: Node): String {
        return if (n.attributes.size > 0)
            n.attributes.joinToString(" ", " ") { "${it.name()}=\"${it.value()}\"" }
        else
            ""
    }
}
