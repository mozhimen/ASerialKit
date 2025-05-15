package com.mozhimen.serialk.html.spanned.impls

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.text.style.ReplacementSpan

/**
 * @ClassName ReplacementSpanDrawTableLink
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/5/13
 * @Version 1.0
 */
/**
 * This span defines how a table should be rendered in the TextKHtml. The default implementation
 * is a cop-out which replaces the HTML table with some text ("[tap for table]" is the default).
 *
 *
 * This is to be used in conjunction with the ClickableTableSpan which will redirect a click to the
 * text some application-defined action (i.e. render the raw HTML in a WebView).
 */
class ReplacementSpanDrawTableLink : ReplacementSpan() {
    companion object {
        private const val DEFAULT_TABLE_LINK_TEXT = ""
        private const val DEFAULT_TEXT_SIZE = 80f
        private const val DEFAULT_TEXT_COLOR = Color.BLUE
    }

    var tableLinkText: String = DEFAULT_TABLE_LINK_TEXT
    var textSize: Float = DEFAULT_TEXT_SIZE
    var textColor: Int = DEFAULT_TEXT_COLOR

    // This sucks, but we need this so that each table can get drawn.
    // Otherwise, we end up with the default table link text (nothing) for earlier tables.
    fun newInstance(): ReplacementSpanDrawTableLink {
        val drawTableLinkSpan = ReplacementSpanDrawTableLink()
        drawTableLinkSpan.tableLinkText = tableLinkText
        drawTableLinkSpan.textSize = textSize
        drawTableLinkSpan.textColor = textColor
        return drawTableLinkSpan
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: FontMetricsInt?): Int {
        val width = paint.measureText(tableLinkText, 0, tableLinkText.length).toInt()
        textSize = paint.textSize
        return width
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val paint2 = Paint()
        paint2.style = Paint.Style.STROKE
        paint2.color = textColor
        paint2.isAntiAlias = true
        paint2.textSize = textSize
        canvas.drawText(tableLinkText, x, bottom.toFloat(), paint2)
    }
}
