package com.mozhimen.serialk.moshi

import com.mozhimen.kotlin.utilk.java.lang.UtilKType
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType

/**
 * @ClassName UtilKJson
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/14 20:23
 * @Version 1.0
 */

@Throws(Exception::class)
inline fun <reified T : Any> T.t2strJson_moshi(indent: String = ""): String =
    UtilMoshiWrapper.t2strJson_moshi(this, indent)

@Throws(Exception::class)
inline fun <reified T> String.strJson2t_moshi(): T? =
    UtilMoshiWrapper.strJson2t_moshi(this)

/////////////////////////////////////////////////////////////////////////////

object UtilMoshiWrapper {

    val moshiBuilder by lazy { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2strJson_moshi(t: T, indent: String = ""): String =
        UtilMoshi.indent_toJson(moshiBuilder.adapter(UtilKType.get<T>()!!), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2strJson_reified_moshi(t: T, indent: String = ""): String =
        UtilMoshi.indent_toJson(moshiBuilder.adapter(T::class.java), t, indent)

    //将 T 序列化为 strJson，指定 parameterizedType，适合复杂类型
    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2strJson_moshi(t: T, parameterizedType: ParameterizedType, indent: String = ""): String =
        UtilMoshi.indent_toJson(moshiBuilder.adapter(parameterizedType), t, indent)

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2t_moshi(strJson: String): T? =
        UtilMoshi.fromJson(moshiBuilder.adapter<T>(UtilKType.get<T>()!!), strJson)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2t_reified_moshi(strJson: String): T? =
        UtilMoshi.fromJson(moshiBuilder.adapter(T::class.java), strJson)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJson2t_moshi(strJson: String, parameterizedType: ParameterizedType): T? =
        UtilMoshi.fromJson(moshiBuilder.adapter<T>(parameterizedType), strJson)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2list_moshi(strJson: String): MutableList<T>? =
        strJson2t_moshi<MutableList<T>>(strJson, Types.newParameterizedType(MutableList::class.java, UtilKType.get<T>()!!))

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2list_reified_moshi(strJson: String): MutableList<T>? =
        strJson2t_moshi<MutableList<T>>(strJson, Types.newParameterizedType(MutableList::class.java, T::class.java))
}