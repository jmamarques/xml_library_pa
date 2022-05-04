package app.command

import structure.XmlAttribute
import javax.swing.JTextField

/**
 * JMA - 04/05/2022 19:40
 **/
class AttributeValueCommand(val newVal: String, val oldVal: String, val attribute: XmlAttribute, val comp: JTextField):
    Command {
    override fun execute(action: Command) {
        attribute.value = newVal
        comp.text = newVal
        comp.repaint()
        comp.revalidate()
    }

    override fun undos() {
        attribute.value = oldVal
        comp.text = oldVal
        comp.repaint()
        comp.revalidate()
    }
}
