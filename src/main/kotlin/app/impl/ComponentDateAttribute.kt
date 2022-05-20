package app.impl

import app.basic.ComponentAttributeG
import app.command.attribute.AttributeValueDateCommand
import org.jdatepicker.impl.JDatePanelImpl
import org.jdatepicker.impl.JDatePickerImpl
import org.jdatepicker.impl.UtilDateModel
import util.DateLabelFormatter
import java.util.*
import javax.swing.Box
import javax.swing.JLabel

/**
 * JMA - 20/05/2022 12:23
 **/
class ComponentDateAttribute: ComponentAttributeG() {

    override fun init() {
        attribute.name = "Date"

        val model =  UtilDateModel()
        model.value = Date()
        attribute.value = DateLabelFormatter().dateFormatter.format(model.value)
        val datePanel =  JDatePanelImpl(model, Properties())
        val datePicker = JDatePickerImpl(datePanel, DateLabelFormatter())
        // Listeners by action
        datePicker.addActionListener {
            notifyObservers {
                it.execute(
                    AttributeValueDateCommand(
                        model.value,
                        DateLabelFormatter().dateFormatter.parse(attribute.value),
                        attribute,
                        datePicker,
                        model
                    )
                )
            }
        }
        // add components to JPanel
        val jLabel = JLabel(attribute.name())
        this.add(jLabel)
        this.add(Box.createVerticalStrut(15))
        this.add(datePicker)
        createPopupMenu()
    }
}
