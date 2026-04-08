package com.mozhimen.serialk.html.spanned.impls

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Parcel
import android.text.Layout
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.core.graphics.withSave

/**
 * @ClassName BulletSpanNumber
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/5/14
 * @Version 1.0
 */

/**
 * Class to use Numbered Lists in TextViews.
 * The span works the same as [android.text.style.BulletSpan] and all lines of the entry have
 * the same leading margin.
 */
class BulletSpanNumber : BulletSpan {
    companion object {
        const val STANDARD_GAP_WIDTH: Int = 10
    }

    ///////////////////////////////////////////////////////////////

    private val mNumberGapWidth: Int
    private val mNumber: String?

    ///////////////////////////////////////////////////////////////

    constructor(gapWidth: Int, number: Int) : super() {
        mNumberGapWidth = gapWidth
        mNumber = "$number."
    }

    constructor(number: Int) : this(STANDARD_GAP_WIDTH, number)

    constructor(src: Parcel) : super(src) {
        mNumberGapWidth = src.readInt()
        mNumber = src.readString()
    }

    ///////////////////////////////////////////////////////////////

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeInt(mNumberGapWidth)
        dest.writeString(mNumber)
    }

    override fun getLeadingMargin(first: Boolean): Int {
        return 2 * STANDARD_GAP_WIDTH + mNumberGapWidth
    }

    override fun drawLeadingMargin(
        c: Canvas, p: Paint, x: Int, dir: Int,
        top: Int, baseline: Int, bottom: Int, text: CharSequence,
        start: Int, end: Int, first: Boolean, l: Layout?,
    ) {
        if ((text as Spanned).getSpanStart(this) == start) {
            val style = p.style

            p.style = Paint.Style.FILL

            if (c.isHardwareAccelerated) {
                c.withSave {
                    drawText(mNumber!!, (x + dir).toFloat(), baseline.toFloat(), p)
                }
            } else {
                c.drawText(mNumber!!, (x + dir).toFloat(), (top + bottom) / 2.0f, p)
            }
            p.style = style
        }
    }
}
