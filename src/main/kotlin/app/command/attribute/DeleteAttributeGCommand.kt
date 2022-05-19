package app.command.attribute

import app.basic.ComponentAttribute
import app.basic.ComponentAttributeG
import app.command.Command
import structure.Attribute
import javax.swing.JComponent

/**
 * JMA - 04/05/2022 19:40
 **/
class DeleteAttributeGCommand(val component: ComponentAttributeG, val attributes: MutableList<Attribute>, val parentComp: JComponent):
    Command {
    override fun execute(action: Command) {
        attributes.remove(component.attribute)
        parentComp.remove(component)
        parentComp.repaint()
        parentComp.revalidate()
    }

    override fun undos() {
        parentComp.add(component)
        attributes.add(component.attribute)
        parentComp.repaint()
        parentComp.revalidate()
    }
}
