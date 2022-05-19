package app.basic

import app.command.Command
import app.command.attribute.CreateAttributeCommand
import app.command.skeleton.CreateNodeCommand
import app.command.skeleton.DeleteNodeGCommand
import app.command.skeleton.NodeNameCommand
import structure.*
import util.XmlUtil
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.GridLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.CompoundBorder

/**
 * JMA - 19/05/2022 19:29
 **/
open class ComponentGeneric: JPanel(), IObservable<Command> {
    open val node: NestedNode = NestedNode("event", mutableListOf(), mutableListOf())
    override val observers: MutableList<Command> = mutableListOf()

    @InjectAdd
    var jComponents = mutableListOf<ComponentAttributeG>()

    fun addObservers(list: MutableList<Command>){
        list.forEach { addObserver(it) }
        list.forEach { jComponents.forEach{ v -> v.addObserver(it)} }
    }

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

    open fun createPopupMenu(){
        val rename = JMenuItem("Rename")
        rename.addActionListener {
            val text = JOptionPane.showInputDialog("entity name")
            if (XmlUtil.isValidEntityName(text)) {
                notifyObservers { it.execute(NodeNameCommand(text, node.name, node, this)) }
            } else {
                JOptionPane.showConfirmDialog(
                    null,
                    "Nome não respeita a syntax do XML", "Error", JOptionPane.DEFAULT_OPTION
                )
            }
        }
        val addAttribute = JMenuItem("Add Attribute")
        addAttribute.addActionListener {
            val text = JOptionPane.showInputDialog("attribute name")
            if (XmlUtil.isValidEntityName(text)) {
                val attribute = XmlAttribute(text)
                notifyObservers {
                    it.execute(
                        CreateAttributeCommand(
                            ComponentAttribute(attribute, observers),
                            node.attributes,
                            this
                        )
                    )
                }
            } else {
                JOptionPane.showConfirmDialog(
                    null,
                    "Nome não respeita a syntax do XML", "Error", JOptionPane.DEFAULT_OPTION
                )
            }
        }
        val addTag = JMenuItem("Add Tag")
        addTag.addActionListener {
            val text = JOptionPane.showInputDialog("tag name")
            if (XmlUtil.isValidEntityName(text)) {
                notifyObservers {
                    it.execute(
                        CreateNodeCommand(
                            ComponentSkeleton(
                                NestedNode(
                                    name = text,
                                    mutableListOf(),
                                    mutableListOf()
                                ), observers
                            ), node.elements, this@ComponentGeneric
                        )
                    )
                }
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
                    notifyObservers {
                        it.execute(
                            DeleteNodeGCommand(
                                this@ComponentGeneric,
                                parentNode.node.elements,
                                parentNode
                            )
                        )
                    }
                }
                else -> {
                    JOptionPane.showConfirmDialog(
                        null,
                        "Não pode apagar root element", "Error", JOptionPane.DEFAULT_OPTION
                    )
                }
            }
            revalidate()
        }

        val addPlugin = JMenuItem("Add Event")
        addPlugin.isEnabled = false
        addPlugin.addActionListener {
            repaint()
            revalidate()
        }

        val popupmenu = JPopupMenu("Actions")
        popupmenu.add(rename)
        popupmenu.add(addAttribute)
        popupmenu.add(addTag)
        popupmenu.add(delete)
        popupmenu.add(addPlugin)


        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@ComponentGeneric, e.x, e.y)
            }
        })
    }
}
