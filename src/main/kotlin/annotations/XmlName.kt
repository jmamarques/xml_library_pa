package annotations

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.CLASS)
annotation class XmlName(val name: String)
