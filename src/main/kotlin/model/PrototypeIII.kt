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
    var intField: Int = 1
    @XmlTagContent
    var doubleField: Double = 01.0
    @XmlName("numericField")
    var numberField: Number = 11
    var strField: String = "a"
    var objField: PrototypeI = PrototypeI()
    var listField: List<String> = listOf("")
    var enumField: TypeEnum =TypeEnum.A
}
