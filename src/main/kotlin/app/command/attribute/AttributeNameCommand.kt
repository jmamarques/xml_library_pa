package app.command.attribute

import app.command.Command
import structure.XmlAttribute
import javax.swing.JLabel

/**
 * JMA - 04/05/2022 19:40
 **/
class AttributeNameCommand(val newVal: String, val oldVal: String, val attribute: XmlAttribute, val comp: JLabel):
    Command {
    override fun execute(action: Command) {
        attribute.name = newVal
        comp.text = newVal
        comp.repaint()
        comp.revalidate()
    }

    override fun undos() {
        attribute.name = oldVal
        comp.text = oldVal
        comp.repaint()
        comp.revalidate()
    }
}
