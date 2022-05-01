package impl

import annotations.XmlIgnore
import annotations.XmlName
import model.PrototypeI
import model.TypeEnum
import org.jetbrains.annotations.NotNull
import structure.LeafNode
import structure.NestedNode
import structure.Node
import util.XmlUtil.Companion.escapingChar
import util.XmlUtil.Companion.isValidEntityName
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KClassifier
import kotlin.reflect.KType
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
            assert(obj != null) { "The value must be different from null" }
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
                Boolean::class -> Optional.of(createLeafNode(obj, "root"))
                else -> {
                    // enums or complex objects
                    if (kClass.isEnum()) {
                        Optional.of(createLeafNode(obj.toString(), "root"))
                    } else {
                        Optional.of(createNestedNode(obj))
                    }
                }
            }

            /*val XmlNameOnClass = kClass.annotations.find { it.annotationClass == XmlName::class }

            val filter = kClass.declaredMembers.filter { it ->
                it.annotations.none { anno -> anno.annotationClass == XmlIgnore::class }
            }

            val filter2 = kClass.declaredMembers.filter { it ->
                it.annotations.any { anno -> anno.annotationClass == XmlIgnore::class }
            }

            val filter3 = kClass.declaredMembers
                .sortedBy { it.name }
                .filter { it.annotations.any { anno -> anno.annotationClass == XmlTagContent::class } }
            // get annotated member
            kClass.annotations*/
            return root
        }

        fun <T> createLeafNode(obj: T, name: String): Node{
            // Validation
            assert(obj != null) { "The value must be different from null" }
            val kClass = obj!!::class
            // get properties
            val xmlName = kClass.annotations.find { it.annotationClass == XmlName::class }
            val propName = if(xmlName != null) (xmlName as XmlName).name else name
            assert(isValidEntityName(propName)) { "Entity shouldn't contain white spaces" }
            // create
            return LeafNode(name= escapingChar(propName), escapingChar("$obj"), mutableListOf())
        }

        fun <T> createNestedNode(obj: T): Node{
            assert(obj != null) { "The obj must be different from null" }
            val kClass = obj!!::class
            // get properties
            val xmlName = kClass.annotations.find { it.annotationClass == XmlName::class }
            var propName = if(xmlName != null) (xmlName as XmlName).name else kClass.simpleName
            if(kClass.simpleName == null) propName = "anonymous"
            assert(isValidEntityName(propName)) { "Entity shouldn't contain white spaces" }
            val elements = mutableListOf<Node>()
            val root = NestedNode(escapingChar(propName), elements, mutableListOf())

            // create
            return root
        }
    }
}

/*fun main() {
//    XmlParser.parseObject("str")
    val prototypeI = PrototypeI()
    val parseObject = XmlParser.parseObject(prototypeI)
    XmlParser.parseObject(TypeEnum.A)
    val createLeafNode = XmlParser.createLeafNode("str", "va lue")
    println(createLeafNode)
}*/
