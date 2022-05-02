package impl

import annotations.XmlIgnore
import annotations.XmlName
import annotations.XmlTagContent
import org.jetbrains.annotations.NotNull
import structure.LeafNode
import structure.NestedNode
import structure.Node
import util.XmlUtil.Companion.escapingChar
import util.XmlUtil.Companion.isValidEntityName
import java.util.*
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.isSubclassOf

/**
 * JMA - 30/04/2022 22:30
 * XML Parser that converts objects to library objects
 **/
class XmlParser {

    companion object {

        private fun KClass<*>?.isEnum() = this is KClass<*> && this.isSubclassOf(Enum::class)

        /**
         * Parse obj to Xml Obj
         */
        fun <T> parseObject(@NotNull obj: T): Optional<Node> {
            require(obj != null) { "The value must be different from null" }
            val kClass = obj!!::class
            // check XmlIgnore
            val containsIgnore = kClass.annotations.any { it.annotationClass == XmlIgnore::class }
            // if you have a XmlIgnore on class level we should ignore that object
            if (containsIgnore) {
                return Optional.empty()
            }
            // match primitive types and other types
            val root: Optional<Node> = when (kClass) {
                String::class, Int::class, Long::class, Float::class, Double::class, Short::class,
                ULong::class, UInt::class, UShort::class, Byte::class, UByte::class, Date::class,
                Boolean::class -> Optional.of(createLeafNode(obj))
                else -> {
                    // enums or complex objects
                    if (kClass.isEnum()) {
                        Optional.of(createLeafNode(obj.toString()))
                    } else if (obj is List<*>) {
                        val nestedNode = NestedNode(escapingChar("root"), mutableListOf(), mutableListOf())
                        obj.forEach { elem -> nestedNode.elements.add(makeDecision(elem)) }
                        Optional.of(nestedNode)
                    } else {
                        Optional.of(createNestedNode(obj))
                    }
                }
            }
            return root
        }

        /**
         * Create a LeafNode
         */
        private fun <T> createLeafNode(obj: T, name: String = "root"): Node {
            // Validation
            require(obj != null) { "The value must be different from null" }
            val kClass = obj!!::class
            // get properties
            val xmlName = kClass.annotations.find { it.annotationClass == XmlName::class }
            val propName =
                if (xmlName != null && (xmlName as XmlName).name.isNotBlank()) xmlName.name else name
            require(isValidEntityName(propName)) { "Entity shouldn't contain white spaces" }
            // create
            return LeafNode(name = escapingChar(propName), escapingChar("$obj"), mutableListOf())
        }

        /**
         * Create a NestedNode
         */
        private fun <T> createNestedNode(obj: T, name: String = ""): Node {
            require(obj != null) { "The obj must be different from null" }
            val kClass = obj!!::class

            // get properties
            var xmlName = kClass.annotations
                .find { it.annotationClass == XmlName::class }
            var propName =
                if (xmlName != null && (xmlName as XmlName).name.isNotBlank()) xmlName.name else if (name.isNotBlank()) name else kClass.simpleName
            if (kClass.simpleName == null) propName = "anonymous"
            require(isValidEntityName(propName)) { "Entity shouldn't contain white spaces" }
            val elements = mutableListOf<Node>()
            val root = NestedNode(escapingChar(propName), elements, mutableListOf())
            // Load all properties
            val props = kClass.declaredMembers
                .filter { it.annotations.indexOf(XmlIgnore()) == -1 }
            // when you have XmlTagContent
            val xmlTagContent = props
                .sortedBy { it.name }
                .filter { it.annotations.indexOf(XmlTagContent()) != -1 }
            if (xmlTagContent.isNotEmpty()) {
                return processXmlTagContent<T>(xmlTagContent, obj, propName, root)
            } else {
                props.forEach {
                    val value = it.call(obj)
                    if (value != null) {
                        val xmlName =
                            it.annotations.find { annotation -> annotation.annotationClass == XmlName::class }
                        val propName =
                            if (xmlName != null && (xmlName as XmlName).name.isNotBlank()) xmlName.name else if (it.name.isNotBlank()) it.name else "anonymous"
                        when (value::class) {
                            String::class, Int::class, Long::class, Float::class, Double::class, Short::class,
                            ULong::class, UInt::class, UShort::class, Byte::class, UByte::class, Date::class,
                            Boolean::class -> elements.add(createLeafNode(value, propName))
                            else -> if (value::class.isEnum()) {
                                elements.add(createLeafNode(value, propName))
                            } else if (value is List<*>) {
                                value.forEach { elem -> elements.add(makeDecision(elem, propName)) }
                            } else {
                                if(!value::class.annotations.any { it.annotationClass == XmlIgnore::class }){
                                    elements.add(createNestedNode(value))
                                }
                            }
                        }
                    }
                }
            }
            // create
            return root
        }

        /**
         * Auxiliary function to lead with @XmlTagContent Logic
         */
        private fun <T> processXmlTagContent(
            xmlTagContent: List<KCallable<*>>,
            obj: T,
            propName: String?,
            root: NestedNode
        ): Node {
            val prop = xmlTagContent[0]
            val value = prop.call(obj)
            // contains value
            return if (value != null) {
                when (value::class) {
                    String::class, Int::class, Long::class, Float::class, Double::class, Short::class,
                    ULong::class, UInt::class, UShort::class, Byte::class, UByte::class, Date::class,
                    Boolean::class -> createLeafNode(value, propName!!)
                    else -> if (value::class.isEnum()) {
                        createLeafNode(obj, propName!!)
                    } else if (value is List<*>) {
                        value.forEach { elem -> root.elements.add(makeDecision(elem, propName!!)) }
                        root
                    } else {
                        if(!value::class.annotations.any { it.annotationClass == XmlIgnore::class }){
                            root.elements.add(createNestedNode(value))
                        }
                        root
                    }
                }
            } else {
                createLeafNode("", propName!!)
            }
        }

        /**
         * Helps to decide which element should we create or not
         */
        private fun <T> makeDecision(obj: T, propName: String = ""): Node {
            require(obj != null) { "The object must be different from null" }
            return when (obj!!::class) {
                String::class, Int::class, Long::class, Float::class, Double::class, Short::class,
                ULong::class, UInt::class, UShort::class, Byte::class, UByte::class, Date::class,
                Boolean::class -> createLeafNode(obj, propName.ifBlank { "object" })
                else -> if (obj!!::class.isEnum()) {
                    createLeafNode(obj, propName.ifBlank { "enum" })
                } else if (obj is List<*>) {
                    val nestedNode =
                        NestedNode(escapingChar(propName.ifBlank { "list" }), mutableListOf(), mutableListOf())
                    obj.forEach { elem -> nestedNode.elements.add(makeDecision(elem, propName)) }
                    nestedNode
                } else {
                    createNestedNode(obj, propName)
                }
            }
        }
    }
}
