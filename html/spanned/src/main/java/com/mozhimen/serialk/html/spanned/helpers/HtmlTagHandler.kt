package com.mozhimen.serialk.html.spanned.helpers

import android.text.Editable
import android.text.Html
import android.text.Layout
import android.text.Spannable
import android.text.Spanned
import android.text.style.AlignmentSpan
import android.text.style.BulletSpan
import android.text.style.LeadingMarginSpan
import android.text.style.StrikethroughSpan
import android.text.style.TypefaceSpan
import android.text.style.URLSpan
import android.view.View
import com.mozhimen.serialk.html.spanned.commons.ITagClickListenerProvider
import com.mozhimen.serialk.html.spanned.commons.ITagHandler
import com.mozhimen.serialk.html.spanned.impls.BulletSpanNumber
import com.mozhimen.serialk.html.spanned.impls.ClickableSpanTable
import com.mozhimen.serialk.html.spanned.impls.ReplacementSpanDrawTableLink
import org.xml.sax.Attributes
import java.util.Locale
import java.util.Stack

/**
 * @ClassName HtmlTagHandler
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/5/14
 * @Version 1.0
 */
/**
 * Some parts of this code are based on android.text.Html
 */
class HtmlTagHandler : ITagHandler {

    companion object {
        const val UNORDERED_LIST: String = "HTML_TEXTVIEW_ESCAPED_UL_TAG"
        const val ORDERED_LIST: String = "HTML_TEXTVIEW_ESCAPED_OL_TAG"
        const val LIST_ITEM: String = "HTML_TEXTVIEW_ESCAPED_LI_TAG"
        const val A_ITEM: String = "HTML_TEXTVIEW_ESCAPED_A_TAG"
        const val PLACEHOLDER_ITEM: String = "HTML_TEXTVIEW_ESCAPED_PLACEHOLDER"
    }

    /**
     * Newer versions of the Android SDK's [Html.TagHandler] handles &lt;ul&gt; and &lt;li&gt;
     * tags itself which means they never get delegated to this class. We want to handle the tags
     * ourselves so before passing the string html into Html.fromHtml(), we can use this method to
     * replace the &lt;ul&gt; and &lt;li&gt; tags with tags of our own.
     *
     * @param strHtml String containing HTML, for example: "**Hello world!**"
     * @return html with replaced  and  *  tags
     * @see [Specific Android SDK Commit](https://github.com/android/platform_frameworks_base/commit/8b36c0bbd1503c61c111feac939193c47f812190)
     */
    fun overrideTags(strHtml: String): String {
        var html = strHtml
        html = "<$PLACEHOLDER_ITEM></$PLACEHOLDER_ITEM>$html"
        html = html.replace("<ul", "<$UNORDERED_LIST")
        html = html.replace("</ul>", "</$UNORDERED_LIST>")
        html = html.replace("<ol", "<$ORDERED_LIST")
        html = html.replace("</ol>", "</$ORDERED_LIST>")
        html = html.replace("<li", "<$LIST_ITEM")
        html = html.replace("</li>", "</$LIST_ITEM>")
        html = html.replace("<a", "<$A_ITEM")
        html = html.replace("</a>", "</$A_ITEM>")
        return html
    }

    /**
     * Keeps track of lists (ol, ul). On bottom of Stack is the outermost list
     * and on top of Stack is the most nested list
     */
    var lists: Stack<String> = Stack()

    /**
     * Tracks indexes of ordered lists so that after a nested list ends
     * we can continue with correct index of outer list
     */
    var olNextIndex: Stack<Int> = Stack()
    /**
     * List indentation in pixels. Nested lists use multiple of this.
     */
    /**
     * Running HTML table string based off of the root table tag. Root table tag being the tag which
     * isn't embedded within any other table tag. Example:
     * <!-- This is the root level opening table tag. This is where we keep track of tables. -->
     * <table>
     * ...
     * <table> <!-- Non-root table tags -->
     * ...
     * </table>
     * ...
     * </table>
     * <!-- This is the root level closing table tag and the end of the string we track. -->
     */
    var tableHtmlBuilder: StringBuilder = StringBuilder()

    /**
     * Tells us which level of table tag we're on; ultimately used to find the root table tag.
     */
    var tableTagLevel: Int = 0

    private var userGivenIndent = -1
    private val defaultIndent = 10
    private val defaultListItemIndent = defaultIndent * 2
    private val defaultBullet = BulletSpan(defaultIndent)
    private var clickableSpanTable: ClickableSpanTable? = null
    private var replacementSpanDrawTableLink: ReplacementSpanDrawTableLink? = null
    private var tagClickListenerProvider: ITagClickListenerProvider? = null

    private class Ul
    private class Ol
    private class A(private val text: String, val href: String?)
    private class Code
    private class Center
    private class Strike
    private class Table
    private class Tr
    private class Th
    private class Td

    override fun handleTag(opening: Boolean, tag: String, output: Editable, attributes: Attributes?): Boolean {
        if (opening) {
            // opening tag
//            if (HtmlTextView.DEBUG) {
//                Log.d(HtmlTextView.TAG, "opening, output: $output")
//            }
            if (tag.equals(UNORDERED_LIST, ignoreCase = true)) {
                lists.push(tag)
            } else if (tag.equals(ORDERED_LIST, ignoreCase = true)) {
                lists.push(tag)
                olNextIndex.push(1)
            } else if (tag.equals(LIST_ITEM, ignoreCase = true)) {
                if (output.isNotEmpty() && output[output.length - 1] != '\n') {
                    output.append("\n")
                }
                if (!lists.isEmpty()) {
                    val parentList = lists.peek()
                    if (parentList.equals(ORDERED_LIST, ignoreCase = true)) {
                        start(output, Ol())
                        olNextIndex.push(olNextIndex.pop() + 1)
                    } else if (parentList.equals(UNORDERED_LIST, ignoreCase = true)) {
                        start(output, Ul())
                    }
                }
            } else if (tag.equals(A_ITEM, ignoreCase = true)) {
                val href = attributes?.getValue("href")
                start(output, A(output.toString(), href))
            } else if (tag.equals("code", ignoreCase = true)) {
                start(output, Code())
            } else if (tag.equals("center", ignoreCase = true)) {
                start(output, Center())
            } else if (tag.equals("s", ignoreCase = true) || tag.equals("strike", ignoreCase = true)) {
                start(output, Strike())
            } else if (tag.equals("table", ignoreCase = true)) {
                start(output, Table())
                if (tableTagLevel == 0) {
                    tableHtmlBuilder = StringBuilder()
                    // We need some text for the table to be replaced by the span because
                    // the other tags will remove their text when their text is extracted
                    output.append("table placeholder")
                }

                tableTagLevel++
            } else if (tag.equals("tr", ignoreCase = true)) {
                start(output, Tr())
            } else if (tag.equals("th", ignoreCase = true)) {
                start(output, Th())
            } else if (tag.equals("td", ignoreCase = true)) {
                start(output, Td())
            } else {
                return false
            }
        } else {
            // closing tag
//            if (HtmlTextView.DEBUG) {
//                Log.d(HtmlTextView.TAG, "closing, output: $output")
//            }

            if (tag.equals(UNORDERED_LIST, ignoreCase = true)) {
                lists.pop()
            } else if (tag.equals(ORDERED_LIST, ignoreCase = true)) {
                lists.pop()
                olNextIndex.pop()
            } else if (tag.equals(LIST_ITEM, ignoreCase = true)) {
                if (!lists.isEmpty()) {
                    val listItemIndent = if (userGivenIndent > -1) (userGivenIndent * 2) else defaultListItemIndent
                    if (lists.peek().equals(UNORDERED_LIST, ignoreCase = true)) {
                        if (output.isNotEmpty() && output[output.length - 1] != '\n') {
                            output.append("\n")
                        }
                        // Nested BulletSpans increases distance between bullet and text, so we must prevent it.
                        var indent = if (userGivenIndent > -1) userGivenIndent else defaultIndent
                        val bullet = if (userGivenIndent > -1) BulletSpan(userGivenIndent) else defaultBullet
                        if (lists.size > 1) {
                            indent -= bullet.getLeadingMargin(true)
                            if (lists.size > 2) {
                                // This get's more complicated when we add a LeadingMarginSpan into the same line:
                                // we have also counter it's effect to BulletSpan
                                indent -= (lists.size - 2) * listItemIndent
                            }
                        }
                        val newBullet = BulletSpan(indent)
                        end(
                            output, Ul::class.java, false,
                            LeadingMarginSpan.Standard(listItemIndent * (lists.size - 1)),
                            newBullet
                        )
                    } else if (lists.peek().equals(ORDERED_LIST, ignoreCase = true)) {
                        if (output.isNotEmpty() && output[output.length - 1] != '\n') {
                            output.append("\n")
                        }

                        // Nested NumberSpans increases distance between number and text, so we must prevent it.
                        var indent = if (userGivenIndent > -1) userGivenIndent else defaultIndent
                        val span: BulletSpanNumber = BulletSpanNumber(indent, olNextIndex.lastElement() - 1)
                        if (lists.size > 1) {
                            indent -= span.getLeadingMargin(true)
                            if (lists.size > 2) {
                                // As with BulletSpan, we need to compensate for the spacing after the number.
                                indent -= (lists.size - 2) * listItemIndent
                            }
                        }
                        val numberSpan: BulletSpanNumber = BulletSpanNumber(indent, olNextIndex.lastElement() - 1)
                        end(
                            output, Ol::class.java, false,
                            LeadingMarginSpan.Standard(listItemIndent * (lists.size - 1)),
                            numberSpan
                        )
                    }
                }
            } else if (tag.equals(A_ITEM, ignoreCase = true)) {
                val a = getLast(output, A::class.java)
                val spanStart = output.getSpanStart(a)
                val spanEnd = output.length
                val href = if (a is A) a.href else null
                val spannedText = output.subSequence(spanStart, spanEnd).toString()
                end(output, A::class.java, false, object : URLSpan(href) {
                    override fun onClick(widget: View) {
                        if (tagClickListenerProvider != null && tagClickListenerProvider?.provideTagClickListener() != null) {
                            val clickConsumed: Boolean = tagClickListenerProvider!!.provideTagClickListener()!!.onClick(widget, spannedText, url)
                            if (!clickConsumed) {
                                super.onClick(widget)
                            }
                        }
                    }
                })
            } else if (tag.equals("code", ignoreCase = true)) {
                end(output, Code::class.java, false, TypefaceSpan("monospace"))
            } else if (tag.equals("center", ignoreCase = true)) {
                end(output, Center::class.java, true, AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER))
            } else if (tag.equals("s", ignoreCase = true) || tag.equals("strike", ignoreCase = true)) {
                end(output, Strike::class.java, false, StrikethroughSpan())
            } else if (tag.equals("table", ignoreCase = true)) {
                tableTagLevel--

                // When we're back at the root-level table
                if (tableTagLevel == 0) {
                    val tableHtml = tableHtmlBuilder.toString()

                    var myClickableTableSpan: ClickableSpanTable? = null
                    if (clickableSpanTable != null) {
                        myClickableTableSpan = clickableSpanTable!!.newInstance()
                        myClickableTableSpan!!.tableHtml=(tableHtml)
                    }

                    var myDrawTableLinkSpan: ReplacementSpanDrawTableLink? = null
                    if (replacementSpanDrawTableLink != null) {
                        myDrawTableLinkSpan = replacementSpanDrawTableLink!!.newInstance()
                    }

                    end(output, Table::class.java, false, myDrawTableLinkSpan, myClickableTableSpan)
                } else {
                    end(output, Table::class.java, false)
                }
            } else if (tag.equals("tr", ignoreCase = true)) {
                end(output, Tr::class.java, false)
            } else if (tag.equals("th", ignoreCase = true)) {
                end(output, Th::class.java, false)
            } else if (tag.equals("td", ignoreCase = true)) {
                end(output, Td::class.java, false)
            } else {
                return false
            }
        }

        storeTableTags(opening, tag)
        return true
    }

    /**
     * If we're arriving at a table tag or are already within a table tag, then we should store it
     * the raw HTML for our ClickableTableSpan
     */
    private fun storeTableTags(opening: Boolean, tag: String) {
        if (tableTagLevel > 0 || tag.equals("table", ignoreCase = true)) {
            tableHtmlBuilder.append("<")
            if (!opening) {
                tableHtmlBuilder.append("/")
            }
            tableHtmlBuilder
                .append(tag.lowercase(Locale.getDefault()))
                .append(">")
        }
    }

    /**
     * Mark the opening tag by using private classes
     */
    private fun start(output: Editable, mark: Any) {
        val len = output.length
        output.setSpan(mark, len, len, Spannable.SPAN_MARK_MARK)

//        if (HtmlTextView.DEBUG) {
//            Log.d(HtmlTextView.TAG, "len: $len")
//        }
    }

    /**
     * Modified from [android.text.Html]
     */
    private fun end(output: Editable, kind: Class<*>, paragraphStyle: Boolean, vararg replaces: Any?) {
        val obj = getLast(output, kind)
        // start of the tag
        val where = output.getSpanStart(obj)
        // end of the tag
        val len = output.length

        // If we're in a table, then we need to store the raw HTML for later
        if (tableTagLevel > 0) {
            val extractedSpanText = extractSpanText(output, kind)
            tableHtmlBuilder.append(extractedSpanText)
        }

        output.removeSpan(obj)

        if (where != len) {
            var thisLen = len
            // paragraph styles like AlignmentSpan need to end with a new line!
            if (paragraphStyle) {
                output.append("\n")
                thisLen++
            }
            for (replace in replaces) {
                output.setSpan(replace, where, thisLen, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

//            if (HtmlTextView.DEBUG) {
//                Log.d(HtmlTextView.TAG, "where: $where")
//                Log.d(HtmlTextView.TAG, "thisLen: $thisLen")
//            }
        }
    }

    /**
     * Returns the text contained within a span and deletes it from the output string
     */
    private fun extractSpanText(output: Editable, kind: Class<*>): CharSequence {
        val obj = getLast(output, kind)
        // start of the tag
        val where = output.getSpanStart(obj)
        // end of the tag
        val len = output.length

        val extractedSpanText = output.subSequence(where, len)
        output.delete(where, len)
        return extractedSpanText
    }

    /**
     * Get last marked position of a specific tag kind (private class)
     */
    private fun getLast(text: Editable, kind: Class<*>): Any? {
        val objs = text.getSpans(0, text.length, kind)
        if (objs.isEmpty()) {
            return null
        } else {
            for (i in objs.size downTo 1) {
                if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
                    return objs[i - 1]
                }
            }
            return null
        }
    }

    // Util method for setting pixels.
    fun setListIndentPx(px: Float) {
        userGivenIndent = Math.round(px)
    }

    fun setClickableTableSpan(clickableTableSpan: ClickableSpanTable?) {
        this.clickableSpanTable = clickableTableSpan
    }

    fun setDrawTableLinkSpan(drawTableLinkSpan: ReplacementSpanDrawTableLink?) {
        this.replacementSpanDrawTableLink = drawTableLinkSpan
    }

    fun setOnClickATagListenerProvider(onClickATagListenerProvider: ITagClickListenerProvider?) {
        this.tagClickListenerProvider = onClickATagListenerProvider
    }
}
