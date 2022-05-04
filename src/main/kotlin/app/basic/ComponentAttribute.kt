package app.basic

import structure.XmlAttribute
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.Box
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

/**
 * JMA - 03/05/2022 22:58
 * Component responsible for XML
 **/
class ComponentAttribute(val attribute: XmlAttribute) : JPanel() {
    init {
        val jTextField = JTextField(5)
        jTextField.let {
            jTextField.text = attribute.value()
        }
        // Listeners by action
        jTextField.addActionListener {
            attribute.value = jTextField.text
        }
        // Listeners by key release
        jTextField.addKeyListener(object: KeyListener{
            override fun keyTyped(e: KeyEvent?) {}
            override fun keyPressed(e: KeyEvent?) {}
            override fun keyReleased(e: KeyEvent?) {
                attribute.value = jTextField.text
            }
        })
        // add components to JPanel
        this.add(JLabel(attribute.name()))
        this.add(Box.createVerticalStrut(15))
        this.add(jTextField)
    }
}
