package model

import annotations.XmlName
import annotations.XmlTagContent

@XmlName("")
class Prototype {
    @XmlTagContent
    val textField: String = ""
    @XmlName("")
    val numberField: Number = 0
    val field1: String = ""
    val field2: String = ""
}