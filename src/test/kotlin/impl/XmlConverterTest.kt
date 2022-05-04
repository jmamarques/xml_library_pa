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
class XmlConverterTest{
    @Test
    fun xmlConvert1Test() {
        val prototypeI = PrototypeI()
        val parseObject = XmlConverter.convertObject(prototypeI)
        println(parseObject)
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
    }

    @Test
    fun xmlConvert2Test() {
        val prototypeII = PrototypeII()
        val parseObject3 = XmlConverter.convertObject(prototypeII)
        println(parseObject3)
        parseObject3.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject3.isEmpty)
    }

    @Test
    fun xmlConvert3Test() {
        val prototypeIII = PrototypeIII()
        val parseObject2 = XmlConverter.convertObject(prototypeIII)
        println(parseObject2)
        parseObject2.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject2.isEmpty)
    }

    @Test
    fun xmlConvertMultipleCasesTest() {
        //list
        var parseObject = XmlConverter.convertObject(listOf("a", "b", "c"))
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // Enum
        parseObject = XmlConverter.convertObject(TypeEnum.D)
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // list Enum
        parseObject = XmlConverter.convertObject(listOf(TypeEnum.D, TypeEnum.A))
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // list prototype
        parseObject = XmlConverter.convertObject(listOf(PrototypeII()))
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // list prototype
        parseObject = XmlConverter.convertObject(listOf(listOf(TypeEnum.D, TypeEnum.A), listOf(TypeEnum.B, TypeEnum.C)))
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)
        // str
        parseObject = XmlConverter.convertObject("test")
        parseObject.ifPresent { XmlUtil.printXml(it) }
        assertFalse(parseObject.isEmpty)

    }
}
