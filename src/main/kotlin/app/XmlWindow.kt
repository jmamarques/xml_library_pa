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
class XmlWindow : JFrame("Xml App"), ComponentAction.ActionEvent{
    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1024, 400)

        val root = NestedNode("root", mutableListOf(), mutableListOf())

        add(ComponentSkeleton(root))
        val componentAction = ComponentAction()
        componentAction.addObserver(this)
        add(componentAction, BorderLayout.SOUTH)
    }

    fun open() {
        isVisible = true
    }

    override fun add() {
        println("add")
    }

    override fun rendo() {
        println("rendo")
    }

    override fun undo() {
        println("undo")
    }
}
