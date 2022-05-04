package app.basic

import structure.NestedNode
import structure.XmlAttribute
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.CompoundBorder

/**
 * JMA - 02/05/2022 22:53
 **/
class ComponentSkeleton(val node: NestedNode) : JPanel() {

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(node.name, 10, 20)
    }

    init {
        layout = GridLayout(0, 3)
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.BLACK, 2, true)
        )
        createPopupMenu()
    }

    private fun createPopupMenu() {
        val rename = JMenuItem("Rename")
        rename.addActionListener {
            val text = JOptionPane.showInputDialog("attribute name")
            node.name = text
            repaint()
            revalidate()
        }
        val addAttribute = JMenuItem("Add Attribute")
        addAttribute.addActionListener {
            val text = JOptionPane.showInputDialog("attribute name")
            val attribute = XmlAttribute(text)
            add(ComponentAttribute(attribute))
            node.attributes.add(attribute)
            revalidate()
        }
        val addTag = JMenuItem("Add Tag")
        addTag.addActionListener {
            val text = JOptionPane.showInputDialog("tag name")
            val nestedNode = NestedNode(name = text, mutableListOf(), mutableListOf())
            node.elements.add(nestedNode)
            add(ComponentSkeleton(nestedNode))
            revalidate()
        }
        val delete = JMenuItem("Delete")
        delete.addActionListener {
            val parent = this.parent
            when(parent::class){
                ComponentSkeleton::class -> {
                    val parentNode = (parent as ComponentSkeleton)
                    parentNode.node.elements.remove(node)
                    parentNode.remove(this@ComponentSkeleton)
                }
                else -> {
                    JOptionPane.showConfirmDialog(null,
                        "Não pode apagar root element", "Error", JOptionPane.DEFAULT_OPTION)
                }
            }
            revalidate()
        }

        val popupmenu = JPopupMenu("Actions")
        popupmenu.add(rename)
        popupmenu.add(addAttribute)
        popupmenu.add(addTag)
        popupmenu.add(delete)


        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@ComponentSkeleton, e.x, e.y)
            }
        })
    }
}


