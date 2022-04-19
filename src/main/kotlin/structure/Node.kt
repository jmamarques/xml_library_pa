package structure

/**
 * JMA - 18/04/2022 23:50
 **/
abstract class Node(val name: String, val attributes: List<Attribute>) {
    abstract fun accept(v: Visitor)
}
