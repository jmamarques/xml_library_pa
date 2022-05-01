package util

/**
 * JMA - 30/04/2022 22:19
 **/
class XmlUtil {

    /**
     * Replace escaping char for valid values
     */
    companion object {
        fun escapingChar(v: String?): String {

            var res = v ?: ""
            //        &lt;	<	less than
            res = res.replace("<", "&lt;")
            //        &gt;	>	greater than
            res = res.replace(">", "&gt;")
            //        &amp;	&	ampersand
            res = res.replace("\\&(?!amp;|gt;|&lt;|apos;|quot;)", "&amp;")
            //        &apos;	'	apostrophe
            res = res.replace("'", "&apos;")
            //        &quot;	"	quotation mark
            res = res.replace("\"", "&quot;")
            return res
        }

        /**
         * Check if a string contains white spaces for entity name
         */
        fun isValidEntityName(v: String?): Boolean {
            return v != null && !v.contains(Regex("\\s+"))
        }
    }
}
