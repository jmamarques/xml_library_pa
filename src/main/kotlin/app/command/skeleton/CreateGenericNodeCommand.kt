package app.command.skeleton

import app.basic.ComponentGeneric
import app.basic.ComponentSkeleton
import app.command.Command
import structure.Node

/**
 * JMA - 20/05/2022 11:29
 **/
class CreateGenericNodeCommand(val component: ComponentGeneric, val elements: MutableList<Node>, val parentComp: ComponentSkeleton):
    Command {
    override fun execute(action: Command) {
        component.addObservers(parentComp.observers)
        parentComp.add(component)
        elements.add(component.node)
        component.jComponents.forEach {
            component.add(it)
            if (component.node.attributes.none { a-> a == it.attribute }) component.node.attributes.add(it.attribute)
         }
        parentComp.repaint()
        parentComp.revalidate()
    }

    override fun undos() {
        component.jComponents.forEach { component.remove(it) }
        elements.remove(component.node)
        parentComp.remove(component)
        parentComp.repaint()
        parentComp.revalidate()
    }
}
