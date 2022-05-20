package app.basic

import app.command.Command
import app.command.attribute.CreateAttributeCommand
import app.command.skeleton.CreateGenericNodeCommand
import app.command.skeleton.CreateNodeCommand
import app.command.skeleton.DeleteNodeCommand
import app.command.skeleton.NodeNameCommand
import impl.XmlDependencyInjection
import structure.IObservable
import structure.NestedNode
import structure.XmlAttribute
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
 * JMA - 02/05/2022 22:53
 **/
class ComponentSkeleton(val node: NestedNode, override val observers: MutableList<Command> = mutableListOf()) :
    JPanel(),
    IObservable<Command> {

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
                            ), node.elements, this
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
                            DeleteNodeCommand(
                                this@ComponentSkeleton,
                                parentNode.node.elements,
                                parentNode
                            )
                        )
                    }
                }
                ComponentGeneric::class -> {
                    val parentNode = (parent as ComponentGeneric)
                    notifyObservers {
                        it.execute(
                            DeleteNodeCommand(
                                this@ComponentSkeleton,
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
        addPlugin.addActionListener {

            notifyObservers {
                it.execute(
                    CreateGenericNodeCommand(
                        XmlDependencyInjection.create(),
                        node.elements,
                        this
                    )
                )
            }
            /* val a = XmlDependencyInjection.create()
             a.addObservers(observers)
             add(a)
             repaint()
             revalidate()*/
            /*comp.observers.addAll(observers)
            jComponents.forEach { comp.add(it) }
            repaint()
            revalidate()*/
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
                    popupmenu.show(this@ComponentSkeleton, e.x, e.y)
            }
        })
    }

}


