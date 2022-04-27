package impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import structure.Attribute
import structure.LeafNode
import structure.NestedNode
import structure.Node

class FilterXmlTagTest{
    @Test
    fun filterXmlTagTest() {
        // Initialization
        val leafNodeNumber: Node = LeafNode(
            "number", "19", mutableListOf(object : Attribute {
                override fun name() = "type"
                override fun value() = "decimal"
            }))
        val leafNodeText : Node = LeafNode ("text","<Zeh>", mutableListOf())
        val leafEmptyNodeText : Node = LeafNode ("text","", mutableListOf())
        val node: Node = NestedNode("entity", mutableListOf(leafNodeNumber), mutableListOf())
        val root: Node = NestedNode("entity", mutableListOf(leafNodeText,node, leafEmptyNodeText), mutableListOf())
        val predicate: (Node) -> Boolean = {it.name == "text" || it.name == "entity" }
        // Validation
        val filterDocument = FilterXmlTag(predicate)
        root.accept(filterDocument)
        // test
        assertTrue(filterDocument.hasFound)
        val xmlToString = XmlToString()
        filterDocument.root!!.accept(xmlToString)
        val result = "<entity>\n" +
                "<text>&lt;Zeh&gt;</text>\n" +
                "<entity/>\n" +
                "<text/>\n" +
                "</entity>\n"
        assertEquals(result,xmlToString.toString())
    }
}