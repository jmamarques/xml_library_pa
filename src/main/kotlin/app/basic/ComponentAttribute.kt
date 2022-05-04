package app.basic

import app.command.*
import app.command.attribute.AttributeNameCommand
import app.command.attribute.AttributeValueCommand
import app.command.attribute.DeleteAttributeCommand
import structure.IObservable
import structure.XmlAttribute
import util.XmlUtil
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

/**
 * JMA - 03/05/2022 22:58
 * Component responsible for XML attribute
 **/
class ComponentAttribute(val attribute: XmlAttribute,override val observers: MutableList<Command> = mutableListOf()) : JPanel(), IObservable<Command> {

    init {
        val jTextField = JTextField(5)
        jTextField.let {
            jTextField.text = attribute.value()
        }
        // Listeners by action
        jTextField.addActionListener {
            notifyObservers {it.execute(AttributeValueCommand(jTextField.text, attribute.value, attribute, jTextField))}
        }
        // Listeners by key release
        jTextField.addKeyListener(object : KeyListener {
            override fun keyTyped(e: KeyEvent?) {}
            override fun keyPressed(e: KeyEvent?) {}
            override fun keyReleased(e: KeyEvent?) {
                // command
                notifyObservers {it.execute(AttributeValueCommand(jTextField.text, attribute.value, attribute, jTextField))}
            }
        })
        // add components to JPanel
        val jLabel = JLabel(attribute.name())
        this.add(jLabel)
        this.add(Box.createVerticalStrut(15))
        this.add(jTextField)
        createPopupMenu()
    }


    private fun createPopupMenu() {
        val rename = JMenuItem("Rename")
        rename.addActionListener {
            val text = JOptionPane.showInputDialog("attribute name")
            if (XmlUtil.isValidEntityName(text)) {
                val jLabel = this.getComponent(0) as JLabel
                val attributeNameCommand = AttributeNameCommand(text, attribute.name, attribute, jLabel)
                notifyObservers {it.execute(attributeNameCommand)}
            } else {
                JOptionPane.showConfirmDialog(
                    null,
                    "Nome não respeita a syntax do XML", "Error", JOptionPane.DEFAULT_OPTION
                )
            }
        }
        val delete = JMenuItem("Delete")
        delete.addActionListener {
            val parent = this.parent
            when (parent::class) {
                ComponentSkeleton::class -> {
                    val parentNode = (parent as ComponentSkeleton)
                    notifyObservers {it.execute(DeleteAttributeCommand(this@ComponentAttribute, parentNode.node.attributes, parentNode))}
                }
                else -> {
                    JOptionPane.showConfirmDialog(
                        null,
                        "Nada para apagar", "Info", JOptionPane.DEFAULT_OPTION
                    )
                }
            }
            revalidate()
        }

        val popupmenu = JPopupMenu("AttributeActions")
        popupmenu.add(rename)
        popupmenu.add(delete)


        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@ComponentAttribute, e.x, e.y)
            }
        })
    }
}
