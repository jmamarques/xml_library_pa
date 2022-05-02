package impl

import sample.PrototypeI
import sample.PrototypeII
import sample.PrototypeIII
import sample.TypeEnum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.XmlUtil

/**
 * JMA - 02/05/2022 01:12
 */
class XmlParserTest{
    @Test
    fun xmlParser1Test() {
        val prototypeI = PrototypeI()
        val parseObject = XmlParser.parseObject(prototypeI)
        println(parseObject)
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
    }

    @Test
    fun xmlParser2Test() {
        val prototypeII = PrototypeII()
        val parseObject3 = XmlParser.parseObject(prototypeII)
        println(parseObject3)
        parseObject3.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject3.isEmpty)
    }

    @Test
    fun xmlParser3Test() {
        val prototypeIII = PrototypeIII()
        val parseObject2 = XmlParser.parseObject(prototypeIII)
        println(parseObject2)
        parseObject2.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject2.isEmpty)
    }

    @Test
    fun xmlParserMultipleCasesTest() {
        //list
        var parseObject = XmlParser.parseObject(listOf("a", "b", "c"))
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // Enum
        parseObject = XmlParser.parseObject(TypeEnum.D)
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // list Enum
        parseObject = XmlParser.parseObject(listOf(TypeEnum.D, TypeEnum.A))
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // list prototype
        parseObject = XmlParser.parseObject(listOf(PrototypeII()))
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // list prototype
        parseObject = XmlParser.parseObject(listOf(listOf(TypeEnum.D, TypeEnum.A), listOf(TypeEnum.B, TypeEnum.C)))
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // str
        parseObject = XmlParser.parseObject("test")
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)

    }
}
