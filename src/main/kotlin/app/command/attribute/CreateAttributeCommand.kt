package app.command.attribute

import app.basic.ComponentAttribute
import app.command.Command
import structure.Attribute
import javax.swing.JComponent

/**
 * JMA - 04/05/2022 19:40
 **/
class CreateAttributeCommand(val component: ComponentAttribute, val attributes: MutableList<Attribute>, val parentComp: JComponent):
    Command {
    override fun execute(action: Command) {
        parentComp.add(component)
        attributes.add(component.attribute)
        parentComp.repaint()
        parentComp.revalidate()
    }

    override fun undos() {
        attributes.remove(component.attribute)
        parentComp.remove(component)
        parentComp.repaint()
        parentComp.revalidate()
    }
}
