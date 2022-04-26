package impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import structure.*

/**
 * JMA - 26/04/2022 00:20
 */
class XmlToStringTest{
    @Test
    fun xmlToString() {
        // Initialization
        val leafNodeNumber: Node = LeafNode(
            "number", "19", mutableListOf(object : Attribute {
                override fun name() = "type"
                override fun value() = "decimal"
            }))
        val leafNodeText : Node = LeafNode ("text","Zeh", mutableListOf())
        val node: Node = NestedNode("entities", mutableListOf(leafNodeNumber), mutableListOf())
        val root: Node = NestedNode("entity", mutableListOf(leafNodeText,node), mutableListOf())
        val xmlToString = XmlToString()
        root.accept(xmlToString)
        // Validator
        assertEquals(true, (object: Visitor{}).visit(node as NestedNode))
        assertDoesNotThrow { xmlToString.toString() }
        assertDoesNotThrow { xmlToString.clear() }
        assertEquals("", xmlToString.toString()) { "Invalid clear buffer" }
    }
}
