package com.mozhimen.serialk.html.spanned.commons

import android.text.Editable
import org.xml.sax.Attributes

/**
 * @ClassName ITagHandler
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/5/14
 * @Version 1.0
 */
interface ITagHandler {
    fun handleTag(opening: Boolean, tag: String, output: Editable, attributes: Attributes?): Boolean
}
