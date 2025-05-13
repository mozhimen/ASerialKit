package com.mozhimen.serialk.html.spanned.commons

import android.view.View

/**
 * @ClassName IATagOnClickListener
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/5/13
 * @Version 1.0
 */
/**
 * This listener can define what happens when the a tag is clicked
 */
interface ITagAOnClickListener {
    /**
     * Notifies of anchor tag click events.
     * @param widget - the [HtmlTextView] instance
     * @param spannedText - the string value of the text spanned
     * @param href - the url for the anchor tag
     * @return indicates whether the click event has been handled
     */
    fun onClick(widget: View, spannedText: String, href: String?): Boolean
}