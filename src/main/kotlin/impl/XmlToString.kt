package impl

import structure.LeafNode
import structure.NestedNode
import structure.Node
import structure.Visitor

/**
 * JMA - 25/04/2022 22:42
 **/
class XmlToString : Visitor {
    private val xmlBuilder: StringBuilder = StringBuilder()

    override fun visit(n: NestedNode): Boolean {
        xmlBuilder.append("<${n.name}${this.attributesToString(n)}>\n")
        return true
    }

    override fun endVisit(n: NestedNode) {
        xmlBuilder.append("</${n.name}>\n")
    }

    override fun visit(l: LeafNode) {
        if (l.element.isNotBlank())
            xmlBuilder.append("<${l.name}${this.attributesToString(l)}>${escapingChar(l.element)}</${l.name}>\n")
        else
            xmlBuilder.append("<${l.name}${this.attributesToString(l)}/>\n")
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

    /**
     * Replace escaping char for valid values
     */
    private fun escapingChar(v: String?): String {
        var res = v ?: ""
        //        &lt;	<	less than
        res = res.replace("<", "&lt;")
        //        &gt;	>	greater than
        res = res.replace(">", "&gt;")
        //        &amp;	&	ampersand
        res = res.replace("\\&(?!amp;|gt;|&lt;|apos;|quot;)", "&amp;")
        //        &apos;	'	apostrophe
        res = res.replace("'", "&apos;")
        //        &quot;	"	quotation mark
        res = res.replace("\"", "&quot;")
        return res
    }
}
