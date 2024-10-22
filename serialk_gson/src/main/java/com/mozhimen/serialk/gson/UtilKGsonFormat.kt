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
fun <T : Any> T.t2strJson_ofGson(): String =
    UtilKGsonFormat.t2strJson_ofGson(this)

fun Any.obj2strJson_ofGson(): String =
    UtilKGsonFormat.obj2strJson_ofGson(this)

/////////////////////////////////////////////////////////////////////////////

fun <T> String.strJson2t_ofGson(typeToken: TypeToken<T>): T? =
    UtilKGsonFormat.strJson2t_ofGson(this, typeToken)

fun <T> String.strJson2t_ofGson(clazz: Class<T>): T? =
    UtilKGsonFormat.strJson2t_ofGson(this, clazz)

fun <T> String.strJson2t_ofGson(type: Type): T? =
    UtilKGsonFormat.strJson2t_ofGson(this, type)

@Throws(Exception::class)
inline fun <reified T> String.strJson2t_ofGson(): T? =
    UtilKGsonFormat.strJson2t_ofGson(this)

@Throws(Exception::class)
inline fun <reified T> String.strJson2t_ofReified_ofGson(): T? =
    UtilKGsonFormat.strJson2t_ofReified_ofGson(this)

@Throws(Exception::class)
inline fun <reified T> String.strJson2t_ofType_ofGson(): T? =
    UtilKGsonFormat.strJson2t_ofType_ofGson(this)

/////////////////////////////////////////////////////////////////////////////

object UtilKGsonFormat : BaseUtilK() {
    val gson by lazy { UtilKGson.get() }

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun <T> t2strJson_ofGson(gson: Gson, t: T): String =
        UtilKGson.toJson(gson, t)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> t2strJson_ofGson(t: T): String =
        t2strJson_ofGson(gson, t)

    @JvmStatic
    @Throws(Exception::class)
    fun obj2strJson_ofGson(obj: Any): String =
        t2strJson_ofGson(obj)

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_ofGson(gson: Gson, strJson: String, typeToken: TypeToken<T>): T? =
        UtilKGson.fromJson(gson, strJson, typeToken)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_ofGson(gson: Gson, strJson: String, clazz: Class<T>): T? =
        UtilKGson.fromJson(gson, strJson, clazz)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_ofGson(gson: Gson, strJson: String, type: Type): T? =
        UtilKGson.fromJson(gson, strJson, type)

    //

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_ofGson(strJson: String, typeToken: TypeToken<T>): T? =
        strJson2t_ofGson(gson, strJson, typeToken)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_ofGson(strJson: String, clazz: Class<T>): T? =
        strJson2t_ofGson(gson, strJson, clazz)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> strJson2t_ofGson(strJson: String, type: Type): T? =
        strJson2t_ofGson(gson, strJson, type)

    //

    @JvmStatic
    @Throws(Exception::class)
    inline fun <reified T> strJson2t_ofGson(strJson: String): T? =
        strJson2t_ofGson(strJson, /*UtilKReflectGenericKotlin.getGenericType<T>()*/UtilKType.get<T>()!!)

    @JvmStatic
    @Throws(Exception::class)
    inline fun <reified T> strJson2t_ofType_ofGson(strJson: String): T? =
        strJson2t_ofGson(strJson, object : TypeToken<T>() {})

    @JvmStatic
    @Throws(Exception::class)
    inline fun <reified T> strJson2t_ofReified_ofGson(strJson: String): T? =
        strJson2t_ofGson(strJson, T::class.java)

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @Throws(Exception::class)
    fun strJson2jsonElement_ofGson(strJson: String): JsonElement? =
        strJson2t_ofGson(strJson, JsonElement::class.java)
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