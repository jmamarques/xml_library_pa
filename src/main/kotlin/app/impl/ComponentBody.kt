package app.impl

import app.basic.ComponentGeneric
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.GridLayout
import javax.swing.BorderFactory
import javax.swing.border.CompoundBorder

/**
 * JMA - 20/05/2022 13:44
 **/
class ComponentBody:ComponentGeneric() {

    override fun init() {
        layout = GridLayout(3, 0)
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.RED, 2, true)
        )
        createPopupMenu()
    }
}
