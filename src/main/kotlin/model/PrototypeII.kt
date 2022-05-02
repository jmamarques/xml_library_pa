package model

import annotations.XmlName

/**
 * JMA - 30/04/2022 22:25
 **/
class PrototypeII {
    var intField: Int = 1
    var doubleField: Double = 01.0
    @XmlName("numericField")
    var numberField: Number = 10
    var strField: String = ""
    var objField: PrototypeI = PrototypeI()
    var listField: List<String> = listOf("a", "b")
    var enumField: TypeEnum =TypeEnum.A
}
