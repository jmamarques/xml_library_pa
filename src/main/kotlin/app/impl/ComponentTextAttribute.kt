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
class ComponentTextAttribute: ComponentAttributeG() {
    override fun init() {
        attribute.name = "description"
        attribute.value = "última aula de PA"
        super.init()
    }
}
