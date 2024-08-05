package com.mozhimen.serialk.gson

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.jvm.Throws

/**
 * @ClassName UtilKGson
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/27
 * @Version 1.0
 */
object UtilKGson {
    @JvmStatic
    fun get(): Gson =
        Gson()

    @JvmStatic
    fun get_ofBuilder(): Gson =
        GsonBuilder().create()

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun <T> toJson(gson: Gson, t: T): String =
        gson.toJson(t)

    /////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun <T> fromJson(gson: Gson, strJson: String, clazz: Class<T>): T? =
        gson.fromJson(strJson, clazz)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> fromJson(gson: Gson, strJson: String, typeOfT: Type): T? =
        gson.fromJson(strJson, typeOfT)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> fromJson(gson: Gson, strJson: String, typeOfT: TypeToken<T>): T? =
        gson.fromJson(strJson, typeOfT)
}