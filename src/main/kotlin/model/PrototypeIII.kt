package model

import annotations.XmlIgnore
import annotations.XmlName
import annotations.XmlTagContent

/**
 * JMA - 30/04/2022 22:26
 **/
class PrototypeIII {
    @XmlTagContent
    @XmlIgnore
    var intField: Int = 0
    var doubleField: Double = 0.0
    @XmlName("numericField")
    var numberField: Number = 0
    var strField: String = ""
    var objField: PrototypeI = PrototypeI()
    var listField: List<String> = listOf("")
    var enumField: TypeEnum =TypeEnum.A
}
