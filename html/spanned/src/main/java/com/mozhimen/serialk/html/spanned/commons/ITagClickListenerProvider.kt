package com.mozhimen.serialk.html.spanned.commons

/**
 * @ClassName ITagClickListenerProvider
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/5/13
 * @Version 1.0
 */
interface ITagClickListenerProvider {
    fun provideTagClickListener(): ITagAOnClickListener?
}