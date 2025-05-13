package com.mozhimen.serialk.html.spanned

import android.text.Html
import android.text.Html.ImageGetter
import android.text.Spanned
import com.mozhimen.kotlin.utilk.android.text.UtilKSpannedWrapper
import com.mozhimen.serialk.html.spanned.commons.ITagAOnClickListener
import com.mozhimen.serialk.html.spanned.commons.ITagClickListenerProvider
import com.mozhimen.serialk.html.spanned.helpers.HtmlSpannedBuilder
import com.mozhimen.serialk.html.spanned.impls.ClickableSpanTable
import com.mozhimen.serialk.html.spanned.impls.ReplacementSpanDrawTableLink

/**
 * @ClassName UtilHtmlSpanned
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/5/13
 * @Version 1.0
 */
object UtilHtmlSpanned {
    @JvmStatic
    fun strHtml2spanned(
        builder: HtmlSpannedBuilder,
    ): Spanned? =
        strHtml2spanned(
            builder.getHtml(), builder.getImageGetter(), builder.getClickableSpanTable(),
            builder.getReplacementSpanDrawTableLink(), object : ITagClickListenerProvider {
                override fun provideTagClickListener(): ITagAOnClickListener? {
                    return builder.getITagAOnClickListener()
                }
            }, builder.getIndent(),
            builder.isRemoveTrailingWhiteSpace()
        )

    @JvmStatic
    fun strHtml2spanned(
        strHtml: String,
        imageGetter: ImageGetter?,
        clickableSpanTable: ClickableSpanTable?,
        replacementSpanDrawTableLink: ReplacementSpanDrawTableLink?,
        tagClickListenerProvider: ITagClickListenerProvider?,
        indent: Float,
        removeTrailingWhiteSpace: Boolean,
    ): Spanned {
        var html = strHtml
        val htmlTagHandler: HtmlTagHandler = HtmlTagHandler()
        htmlTagHandler.setClickableTableSpan(clickableTableSpan)
        htmlTagHandler.setDrawTableLinkSpan(drawTableLinkSpan)
        htmlTagHandler.setOnClickATagListenerProvider(tagClickListenerProvider)
        htmlTagHandler.setListIndentPx(indent)
        html = htmlTagHandler.overrideTags(html)
        val formattedHtml = if (removeTrailingWhiteSpace) {
            UtilKSpannedWrapper.removeLastBottomPadding(Html.fromHtml(html, imageGetter, WrapperContentHandler(htmlTagHandler)))
        } else {
            Html.fromHtml(html, imageGetter, WrapperContentHandler(htmlTagHandler))
        }
        return formattedHtml
    }
}