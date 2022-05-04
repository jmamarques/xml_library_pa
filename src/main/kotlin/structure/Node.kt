package structure

/**
 * JMA - 18/04/2022 23:50
 **/
abstract class Node(var name: String, val attributes: MutableList<Attribute>) {
    abstract fun accept(v: Visitor)
}
