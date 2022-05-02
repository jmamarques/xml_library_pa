package sample

import annotations.XmlIgnore
import annotations.XmlName

@XmlName("PropA")
class PrototypeI {
    @XmlIgnore
    @XmlName("error")
    var intField: Int = 4
    var doubleField: Double = 40.0
    @XmlName("numericField")
    var numberField: Number = 4
    var strField: String = "avc"
    var listField: List<String> = listOf("")
    @XmlName("activity")
    var enumField: TypeEnum =TypeEnum.A
}
