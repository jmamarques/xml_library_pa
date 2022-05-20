package util

import java.text.SimpleDateFormat
import java.util.*
import javax.swing.JFormattedTextField.AbstractFormatter

/**
 * JMA - 20/05/2022 13:00
 **/
class DateLabelFormatter : AbstractFormatter() {
    val datePattern = "yyyy-MM-dd"
    val dateFormatter = SimpleDateFormat(datePattern)
    override fun stringToValue(text: String?): Any {
        return dateFormatter.parseObject(text)
    }

    override fun valueToString(value: Any?): String {
        if (value != null) {
            val cal = value as Calendar
            return dateFormatter.format(cal.time)
        }
        return ""
    }

}
