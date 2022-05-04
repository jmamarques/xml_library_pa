package app.basic

import apptest.button
import structure.IObservable
import java.awt.Dimension
import javax.swing.JPanel

/**
 * JMA - 02/05/2022 23:51
 **/
class ComponentAction(override val observers: MutableList<ActionEvent> = mutableListOf()) : JPanel(), IObservable<ComponentAction.ActionEvent> {
    interface ActionEvent {
        fun add(){}
        fun rendo(){}
        fun undo(){}
    }
    init {
        size = Dimension(20, 20)

        add(button("save") {
            notifyObservers { it.add() }
        })
        add(button("rendo") {
            notifyObservers { it.rendo() }
        })
        add(button("undo") {
            notifyObservers { it.undo() }
        })

    }
}
