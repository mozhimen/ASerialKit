package com.mozhimen.serialk.html.spanned.impls

import android.text.style.ClickableSpan

/**
 * @ClassName ClickableSpanTable
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/5/13
 * @Version 1.0
 */
/**
 * This span defines what should happen if a table is clicked. This abstract class is defined so
 * that applications can access the raw table HTML and do whatever they'd like to render it (e.g.
 * show it in a WebView).
 */
abstract class ClickableSpanTable : ClickableSpan() {
    var tableHtml: String? = null

    // This sucks, but we need this so that each table can get its own ClickableTableSpan.
    // Otherwise, we end up removing the clicking from earlier tables.
    abstract fun newInstance(): ClickableSpanTable?
}
