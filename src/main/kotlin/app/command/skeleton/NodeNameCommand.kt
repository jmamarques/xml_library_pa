package app.command.skeleton

import app.command.Command
import structure.NestedNode
import structure.XmlAttribute
import javax.swing.JComponent
import javax.swing.JLabel

/**
 * JMA - 04/05/2022 19:40
 **/
class NodeNameCommand(val newVal: String, val oldVal: String, val node: NestedNode, val comp: JComponent):
    Command {
    override fun execute(action: Command) {
        node.name = newVal
        comp.repaint()
        comp.revalidate()
    }

    override fun undos() {
        node.name = oldVal
        comp.repaint()
        comp.revalidate()
    }
}
