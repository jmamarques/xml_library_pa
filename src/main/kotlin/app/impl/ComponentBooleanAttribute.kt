package app.impl

import app.basic.ComponentAttributeG
import app.command.attribute.AttributeValueCheckboxCommand
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.Box
import javax.swing.JCheckBox
import javax.swing.JLabel

/**
 * JMA - 20/05/2022 12:23
 **/
class ComponentBooleanAttribute: ComponentAttributeG() {
    override fun init() {
        attribute.name = "Mandatory"
        attribute.value = "false"
        val jCheckBox = JCheckBox()
        jCheckBox.let {
            jCheckBox.isSelected = false
        }
        // Listeners by action
        jCheckBox.addActionListener {
            notifyObservers {
                it.execute(
                    AttributeValueCheckboxCommand(
                        jCheckBox.isSelected,
                        attribute.value == "true",
                        attribute,
                        jCheckBox
                    )
                )
            }
        }
        // add components to JPanel
        val jLabel = JLabel(attribute.name())
        this.add(jLabel)
        this.add(Box.createVerticalStrut(15))
        this.add(jCheckBox)
        createPopupMenu()
    }
}
