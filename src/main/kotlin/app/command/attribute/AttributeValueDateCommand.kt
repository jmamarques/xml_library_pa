package app.command.attribute

import app.command.Command
import org.jdatepicker.impl.JDatePickerImpl
import org.jdatepicker.impl.UtilDateModel
import structure.XmlAttribute
import util.DateLabelFormatter
import java.util.Date
import javax.swing.JCheckBox
import javax.swing.JTextField

/**
 * JMA - 04/05/2022 19:40
 **/
class AttributeValueDateCommand(val newVal: Date, val oldVal: Date, val attribute: XmlAttribute, val comp: JDatePickerImpl, val model: UtilDateModel):
    Command {
    override fun execute(action: Command) {
        attribute.value = DateLabelFormatter().dateFormatter.format(newVal)
        model.value = newVal
        comp.repaint()
        comp.revalidate()
    }

    override fun undos() {
        attribute.value = DateLabelFormatter().dateFormatter.format(oldVal)
        model.value = oldVal
        comp.repaint()
        comp.revalidate()
    }
}
