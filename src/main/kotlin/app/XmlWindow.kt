package app

import app.basic.ComponentAction
import app.basic.ComponentSkeleton
import structure.NestedNode
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame

/**
 * JMA - 02/05/2022 22:37
 **/
class XmlWindow : JFrame("Xml App") {
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1024, 400)

        val root = NestedNode("root", mutableListOf(), mutableListOf())

        add(ComponentSkeleton(root))
        add(ComponentAction(), BorderLayout.SOUTH)
    }

    fun open() {
        isVisible = true
    }

}
