package app.basic

import apptest.button
import java.awt.Dimension
import javax.swing.JPanel

/**
 * JMA - 02/05/2022 23:51
 **/
class ComponentAction() : JPanel() {
    init {
        size = Dimension(20, 20)

        add(button("save") {
            TODO("FIRE EVENT SAVE")
        })
        add(button("undo") {
            TODO("FIRE EVENT UNDO")
        })
    }
}
