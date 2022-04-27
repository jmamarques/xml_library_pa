package impl

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import structure.Attribute
import structure.LeafNode
import structure.NestedNode
import structure.Node

/**
 * JMA - 27/04/2022 01:11
 */
internal class FindMultipleXmlTagTest{
    @Test
    fun findXmlTagTest() {
        // Initialization
        val leafNodeNumber: Node = LeafNode(
            "number", "19", mutableListOf(object : Attribute {
                override fun name() = "type"
                override fun value() = "decimal"
            }))
        val leafNodeText : Node = LeafNode ("text","<Zeh>", mutableListOf())
        val leafEmptyNodeText : Node = LeafNode ("text","", mutableListOf())
        val node: Node = NestedNode("entities", mutableListOf(leafNodeNumber), mutableListOf())
        val root: Node = NestedNode("entity", mutableListOf(leafNodeText,node, leafEmptyNodeText), mutableListOf())
        // Validation
        // try to find text tag
        val textFindXmlTag = FindMultipleXmlTag { it.name == "text" }
        root.accept(textFindXmlTag)
        assertEquals(true, textFindXmlTag.hasFound)
        assertEquals(2, textFindXmlTag.nodes.size)
        // try to find a node do not belong to root
        val nonExistsFindXmlTag = FindMultipleXmlTag { it.name == "does not exist" }
        root.accept(nonExistsFindXmlTag)
        assertEquals(false, nonExistsFindXmlTag.hasFound)
    }
}
