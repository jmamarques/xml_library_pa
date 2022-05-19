package app.command.skeleton

import app.basic.ComponentGeneric
import app.basic.ComponentSkeleton
import app.command.Command
import structure.Node
import javax.swing.JComponent

/**
 * JMA - 04/05/2022 19:40
 **/
class CreateNodeGCommand(val component: ComponentGeneric, val elements: MutableList<Node>, val parentComp: JComponent):
    Command {
    override fun execute(action: Command) {
        parentComp.add(component)
        elements.add(component.node)
        parentComp.repaint()
        parentComp.revalidate()
    }

    override fun undos() {
        elements.remove(component.node)
        parentComp.remove(component)
        parentComp.repaint()
        parentComp.revalidate()
    }
}
