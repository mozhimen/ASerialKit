package com.mozhimen.serialk.html.spanned.helpers

import android.text.Html.ImageGetter
import com.mozhimen.serialk.html.spanned.commons.ITagAOnClickListener
import com.mozhimen.serialk.html.spanned.impls.ClickableSpanTable
import com.mozhimen.serialk.html.spanned.impls.ReplacementSpanDrawTableLink

/**
 * @ClassName HtmlSpannedBuilder
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/5/13
 * @Version 1.0
 */
class HtmlSpannedBuilder {
    private var html: String = ""
    private var imageGetter: ImageGetter? = null
    private var clickableTableSpan: ClickableSpanTable? = null
    private var drawTableLinkSpan: ReplacementSpanDrawTableLink? = null
    private var onClickATagListener: ITagAOnClickListener? = null
    private var indent = 24.0f
    private var removeTrailingWhiteSpace = true

    fun getHtml(): String {
        return html
    }

    fun getImageGetter(): ImageGetter? {
        return imageGetter
    }

    fun getClickableSpanTable(): ClickableSpanTable? {
        return clickableTableSpan
    }

    fun getReplacementSpanDrawTableLink(): ReplacementSpanDrawTableLink? {
        return drawTableLinkSpan
    }

    fun getITagAOnClickListener(): ITagAOnClickListener? {
        return onClickATagListener
    }

    fun getIndent(): Float {
        return indent
    }

    fun isRemoveTrailingWhiteSpace(): Boolean {
        return removeTrailingWhiteSpace
    }

    //////////////////////////////////////////////////////////////////////////

    fun setHtml(html: String): HtmlSpannedBuilder {
        this.html = html
        return this
    }

    fun setImageGetter(imageGetter: ImageGetter?): HtmlSpannedBuilder {
        this.imageGetter = imageGetter
        return this
    }

    fun setClickableSpanTable(clickableTableSpan: ClickableSpanTable?): HtmlSpannedBuilder {
        this.clickableTableSpan = clickableTableSpan
        return this
    }

    fun setReplacementSpanDrawTableLink(drawTableLinkSpan: ReplacementSpanDrawTableLink?): HtmlSpannedBuilder {
        this.drawTableLinkSpan = drawTableLinkSpan
        return this
    }

    fun setITagAOnClickListener(onClickATagListener: ITagAOnClickListener?): HtmlSpannedBuilder  {
        this.onClickATagListener = onClickATagListener
        return this
    }

    fun setIndent(indent: Float): HtmlSpannedBuilder {
        this.indent = indent
        return this
    }

    fun setRemoveTrailingWhiteSpace(removeTrailingWhiteSpace: Boolean): HtmlSpannedBuilder {
        this.removeTrailingWhiteSpace = removeTrailingWhiteSpace
        return this
    }
}