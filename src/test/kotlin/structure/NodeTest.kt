package structure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * JMA - 25/04/2022 21:21
 */
internal class NodeTest {

    @Test
    fun nodeTest() {
        // Initialization
        val leafNodeNumber: Node = LeafNode(
            "number", "19", mutableListOf(object : Attribute {
                override fun name() = "type"
                override fun value() = "decimal"
            }))
        val leafNodeText : Node = LeafNode ("text","Zeh", mutableListOf())
        val node: Node = NestedNode("entities", mutableListOf(leafNodeNumber), mutableListOf())
        val root: Node = NestedNode("entity", mutableListOf(leafNodeText,node), mutableListOf())
        // Test
        assertEquals(root.attributes.size, 0) { "it has attributes" }
        assertTrue(root is NestedNode) { "invalid root" }
        assertEquals((root as NestedNode).elements.size, 2) { "invalid number of children" }
    }
}
