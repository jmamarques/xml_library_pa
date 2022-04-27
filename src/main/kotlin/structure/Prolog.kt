package structure

/**
 * JMA - 18/04/2022 22:55
 **/
interface Prolog{
    fun version(): String = "1.0"
    fun encoding(): String = "UTF-8"
    fun header(): String = "<?xml version=\"${version()}\" encoding=\"${encoding()}\"?>\n"
}
