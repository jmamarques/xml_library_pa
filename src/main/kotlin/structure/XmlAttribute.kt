package structure

/**
 * JMA - 03/05/2022 23:32
 **/
class XmlAttribute(var name: String, var value: String = "") : Attribute {
    override fun name(): String {
        return name
    }

    override fun value(): String {
        return value
    }

    override fun toString(): String {
        return "XmlAttribute(name='$name', value='$value')"
    }
}
