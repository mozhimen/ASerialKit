package com.mozhimen.serialk.gson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.mozhimen.kotlin.utilk.bases.BaseUtilK
import com.mozhimen.kotlin.utilk.java.lang.UtilKType
import java.lang.reflect.Type
import kotlin.jvm.Throws

/**
 * @ClassName UtilKJsonGson
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 17:21
 * @Version 1.0
 */
@Throws(Exception::class)
fun <T : Any> T.t2strJson_gson(): String =
    UtilGsonFormat.t2strJson_gson(this)

fun Any.obj2strJson_gson(): String =
    UtilGsonFormat.obj2strJson_gson(this)

/////////////////////////////////////////////////////////////////////////////

fun <T> String.strJson2t_gson(typeToken: TypeToken<T>): T? =
    UtilGsonFormat.strJson2t_gson(this, typeToken)

fun <T> String.strJson2t_gson(clazz: Class<T>): T? =
    UtilGsonFormat.strJson2t_gson(this, clazz)

fun <T> String.strJson2t_gson(type: Type): T? =
    UtilGsonFormat.strJson2t_gson(this, type)

@Throws(Exception::class)
inline fun <reified T> String.strJson2t_gson(): T? =
    UtilGsonFormat.strJson2t_gson(this)

@Throws(Exception::class)
inline fun <reified T> String.strJson2t_reified_gson(): T? =
    UtilGsonFormat.strJson2t_reified_gson(this)

@Throws(Exception::class)
inline fun <reified T> String.strJson2t_type_gson(): T? =
    UtilGsonFormat.strJson2t_type_gson(this)

/////////////////////////////////////////////////////////////////////////////

object UtilGsonFormat : BaseUtilK() {
    val gson by lazy { UtilGson.get() }

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun <T> t2strJson_gson(gson: Gson, t: T): String =
        UtilGson.toJson(gson, t)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> t2strJson_gson(t: T): String =
        t2strJson_gson(gson, t)

    @JvmStatic
    @Throws(Exception::class)
    fun obj2strJson_gson(obj: Any): String =
        t2strJson_gson(obj)

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_gson(gson: Gson, strJson: String, typeToken: TypeToken<T>): T? =
        UtilGson.fromJson(gson, strJson, typeToken)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_gson(gson: Gson, strJson: String, clazz: Class<T>): T? =
        UtilGson.fromJson(gson, strJson, clazz)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_gson(gson: Gson, strJson: String, type: Type): T? =
        UtilGson.fromJson(gson, strJson, type)

    //

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_gson(strJson: String, typeToken: TypeToken<T>): T? =
        strJson2t_gson(gson, strJson, typeToken)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_gson(strJson: String, clazz: Class<T>): T? =
        strJson2t_gson(gson, strJson, clazz)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_gson(strJson: String, type: Type): T? =
        strJson2t_gson(gson, strJson, type)

    //

    @JvmStatic
    @Throws(Exception::class)
    inline fun <reified T> strJson2t_gson(strJson: String): T? =
        strJson2t_gson(strJson, /*UtilKReflectGenericKotlin.getGenericType<T>()*/UtilKType.get<T>()!!)

    @JvmStatic
    @Throws(Exception::class)
    inline fun <reified T> strJson2t_type_gson(strJson: String): T? =
        strJson2t_gson(strJson, object : TypeToken<T>() {})

    @JvmStatic
    @Throws(Exception::class)
    inline fun <reified T> strJson2t_reified_gson(strJson: String): T? =
        strJson2t_gson(strJson, T::class.java)

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun strJson2jsonElement_gson(strJson: String): JsonElement? =
        strJson2t_gson(strJson, JsonElement::class.java)
}

//    private val _gsonWithField by lazy { GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create() }
//    private val _gsonWithExpose by lazy { GsonBuilder().excludeFieldsExExposeAnnotation().create() }
//
//    @JvmStatic
//    fun obj2JsonWithField(obj: Any): String =
//        _gsonWithField.toJson(obj)
//
//    @JvmStatic
//    fun <T> json2TWithField(strJson: String, clazz: Class<T>): T =
//        _gsonWithField.fromJson(strJson, clazz)
//
//    @JvmStatic
//    fun obj2JsonWithExpose(obj: Any): String =
//        _gsonWithExpose.toJson(obj)
//
//    @JvmStatic
//    fun <T> json2TWithExpose(strJson: String, clazz: Class<T>): T? =
//        _gsonWithExpose.fromJson(strJson, clazz)

//fun Any.toJsonWithExposeGson(): String =
//    UtilKJsonGson.obj2JsonWithExpose(this)

//fun <T> String.toTWithExposeGson(clazz: Class<T>): T? =
//    UtilKJsonGson.json2TWithExpose(this, clazz)