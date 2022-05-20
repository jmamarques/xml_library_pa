package impl

import app.basic.ComponentAttributeG
import app.basic.ComponentGeneric
import app.basic.ComponentSkeleton
import app.command.Command
import structure.Inject
import structure.InjectAdd
import structure.NestedNode
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.isAccessible

/**
 * JMA - 19/05/2022 20:10
 **/
class XmlDependencyInjection {

    companion object{
        private fun loadProperties(): Properties{
            val file = File("src/main/resources/xml.properties")

            val prop = Properties()

            FileInputStream(file).use {
                prop.load(it)
            }
            return prop
        }
        fun <T:Any> create(c: KClass<T>): T {
            val obj = c.createInstance()
            val property: String = loadProperties().getProperty("Xml.component")
                ?: throw Exception("Fill configuration file xml.properties")
            c.declaredMemberProperties.filter { it.hasAnnotation<Inject>() }
                .forEach {
                    val clazz: KClass<*> = Class.forName(property).kotlin
                    it.isAccessible = true

                    (it as KMutableProperty<*>).setter.call(obj, clazz.createInstance())
                }
            return obj
        }

        fun create(): ComponentGeneric{
            val loadProperties = loadProperties()
            val property: String = loadProperties.getProperty("Xml.component")
                ?: throw Exception("Fill configuration file xml.properties")
            val clazz: KClass<*> = Class.forName(property).kotlin
            val obj = clazz.createInstance()

            clazz.declaredMemberProperties.filter { it.hasAnnotation<InjectAdd>() }
                .forEach {
                    val clazz: KClass<*> = Class.forName(property).kotlin
                    if(clazz != ComponentGeneric::class && clazz.java.superclass != ComponentGeneric::class.java) throw Exception("Invalid Component")
                    it.isAccessible = true
                    val call = (it as KMutableProperty<*>).getter.call(obj)
                    val props = loadProperties.getProperty("Xml.attributes")
                    props?.split(",")?.forEach { v->
                        val c: KClass<*> = Class.forName(v).kotlin
                        if(c != ComponentAttributeG::class && c.java.superclass != ComponentAttributeG::class.java) throw Exception("Invalid Attribute Component")
                        val o = c.createInstance() as ComponentAttributeG
                        (call as MutableList<ComponentAttributeG>).add(o)
                    }
                }
            return obj as ComponentGeneric
        }
    }

}
