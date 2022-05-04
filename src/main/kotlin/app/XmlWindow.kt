package app

import app.basic.ComponentAction
import app.basic.ComponentSkeleton
import structure.Command
import structure.NestedNode
import structure.Node
import util.XmlUtil
import java.awt.BorderLayout
import java.awt.Dimension
import java.io.File
import java.util.*
import javax.swing.JFrame
import javax.swing.JOptionPane

/**
 * JMA - 02/05/2022 22:37
 **/
class XmlWindow : JFrame("Xml App"), ComponentAction.ActionEvent{
    private var stack: Stack<Command> = Stack()
    private var reverse: Stack<Command> = Stack()
    private val root: NestedNode = NestedNode("root", mutableListOf(), mutableListOf())

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1024, 400)

        add(ComponentSkeleton(root))
        val componentAction = ComponentAction()
        componentAction.addObserver(this)
        add(componentAction, BorderLayout.SOUTH)
    }

    fun open() {
        isVisible = true
    }

    override fun add() {
        val filePath = File(System.getProperty("user.dir")).absolutePath
        val fileName = "data.xml"
        val path = "$filePath\\$fileName"
        val file = File(path)
        println(path)
        val isNewFileCreated :Boolean = file.createNewFile()
        // create a new file
        file.writeText(XmlUtil.toStringXml(root))
        JOptionPane.showConfirmDialog(null,
            "Path:$path | New file: $isNewFileCreated", "Info - Add", JOptionPane.DEFAULT_OPTION)
        println("add")
    }

    override fun rendo() {
        if (!reverse.isEmpty()){
            val action = reverse.pop()
            action.execute { action }
            stack.push(action)
        } else{
            JOptionPane.showConfirmDialog(null,
                "Não tem elementos na pilha de execução", "Info - Rendo", JOptionPane.DEFAULT_OPTION)
        }
        println("rendo")
    }

    override fun undo() {
        if (!stack.isEmpty()){
            val action = stack.pop()
            action.undo()
            reverse.push(action)
        } else {
            JOptionPane.showConfirmDialog(null,
                "Não tem elementos na pilha de execução", "Info - Undo", JOptionPane.DEFAULT_OPTION)
        }
        println("undo")
    }
}
