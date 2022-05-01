package impl

import annotations.XmlIgnore
import annotations.XmlName
import annotations.XmlTagContent
import model.PrototypeI
import model.PrototypeIII
import model.TypeEnum
import org.jetbrains.annotations.NotNull
import structure.LeafNode
import structure.NestedNode
import structure.Node
import util.XmlUtil.Companion.escapingChar
import util.XmlUtil.Companion.isValidEntityName
import java.util.*
import kotlin.reflect.KCallable
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.isSubclassOf

/**
 * JMA - 30/04/2022 22:30
 * XML Parser that converts objects to library objects
 **/
class XmlParser {

    companion object {

        fun KClassifier?.isEnum() = this is KClass<*> && this.isSubclassOf(Enum::class)
        fun KClass<*>?.isEnum() = this is KClass<*> && this.isSubclassOf(Enum::class)
        fun KType?.isEnum() = this is KClass<*> && this.isSubclassOf(Enum::class)

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
            val node: Optional<Node> = Optional.empty()
            // match primitive types and other types
            val root: Optional<Node> = when (kClass) {
                String::class, Int::class, Long::class, Float::class, Double::class, Short::class,
                ULong::class, UInt::class, UShort::class, Byte::class, UByte::class, Date::class,
                Boolean::class -> Optional.of(createLeafNode(obj))
                else -> {
                    // enums or complex objects
                    if (kClass.isEnum()) {
                        Optional.of(createLeafNode(obj.toString()))
                    } else {
                        Optional.of(createNestedNode(obj))
                    }
                }
            }
            return root
        }

        fun <T> createLeafNode(obj: T, name: String = "root"): Node {
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

        fun <T> createNestedNode(obj: T, name: String = ""): Node {
            require(obj != null) { "The obj must be different from null" }
            val kClass = obj!!::class

            if (kClass.annotations.any { it.annotationClass == XmlIgnore::class }) {
                TODO("We do not support class with XmlIgnore")
            }
            // get properties
            val xmlName = kClass.annotations
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
                    } else {
                        root.elements.add(createNestedNode(value))
                        root
                    }
                }
            } else {
                createLeafNode("", propName!!)
            }
        }
    }
}

fun main() {
//    XmlParser.parseObject("str")
    val prototypeI = PrototypeI()
    val prototypeIII = PrototypeIII()
    val parseObject = XmlParser.parseObject(prototypeI)
    val parseObject2 = XmlParser.parseObject(prototypeIII)
    println(XmlParser.parseObject(TypeEnum.A))
    val createLeafNode = XmlParser.createLeafNode("str", "value")
    println(createLeafNode)
    println(parseObject)
    println(parseObject2)
}
