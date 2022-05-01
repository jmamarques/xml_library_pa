package model

import annotations.XmlIgnore
import annotations.XmlName

@XmlName("PropA")
class PrototypeI {
    @XmlIgnore
    var intField: Int = 0
    var doubleField: Double = 0.0
    @XmlName("numericField")
    var numberField: Number = 0
    var strField: String = ""
    var listField: List<String> = listOf("")
    var enumField: TypeEnum =TypeEnum.A
}
