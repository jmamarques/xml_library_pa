package app.command.attribute

import app.command.Command
import structure.XmlAttribute
import javax.swing.JCheckBox
import javax.swing.JTextField

/**
 * JMA - 04/05/2022 19:40
 **/
class AttributeValueCheckboxCommand(val newVal: Boolean, val oldVal: Boolean, val attribute: XmlAttribute, val comp: JCheckBox):
    Command {
    override fun execute(action: Command) {
        attribute.value = if(newVal) "true" else "false"
        comp.isSelected = newVal
        comp.repaint()
        comp.revalidate()
    }

    override fun undos() {
        attribute.value = if(oldVal) "true" else "false"
        comp.isSelected = oldVal
        comp.repaint()
        comp.revalidate()
    }
}
