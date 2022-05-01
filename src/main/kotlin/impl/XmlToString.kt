package impl

import structure.LeafNode
import structure.NestedNode
import structure.Node
import structure.Visitor
import util.XmlUtil
import util.XmlUtil.Companion.escapingChar

/**
 * JMA - 25/04/2022 22:42
 **/
class XmlToString : Visitor {
    private val xmlBuilder: StringBuilder = StringBuilder()

    override fun visit(n: NestedNode): Boolean {
        if(n.elements.isEmpty()){
            xmlBuilder.append("<${escapingChar(n.name)}${this.attributesToString(n)}/>\n")
        } else {
            xmlBuilder.append("<${escapingChar(n.name)}${this.attributesToString(n)}>\n")
        }
        return true
    }

    override fun endVisit(n: NestedNode) {
        if(n.elements.isNotEmpty()){
            xmlBuilder.append("</${escapingChar(n.name)}>\n")
        }
    }

    override fun visit(l: LeafNode) {
        val name = escapingChar(l.name)
        if (l.element.isNotBlank()) {
            val value = escapingChar(l.element)
            xmlBuilder.append(
                "<$name${this.attributesToString(l)}>$value</$name>\n"
            )
        } else
            xmlBuilder.append("<$name${this.attributesToString(l)}/>\n")
    }

    /**
     * Clear Visitor builder
     */
    fun clear() {
        xmlBuilder.clear()
    }

    /**
     * Textual representation on XML Objects
     */
    override fun toString(): String {
        return xmlBuilder.toString()
    }

    /**
     * Auxiliary fun to populate attributes
     */
    private fun attributesToString(n: Node): String {
        return if (n.attributes.size > 0)
            n.attributes.joinToString(" ", " ") { "${escapingChar(it.name())}=\"${escapingChar(it.value())}\"" }
        else
            ""
    }

}
