package com.mozhimen.serialk.gson

import org.json.JSONObject

/**
 * @ClassName UtilKAnyFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/10 0:29
 * @Version 1.0
 */
fun Any.obj2jSONObject(): JSONObject =
    UtilAnyFormat.obj2jSONObject(this)

object UtilAnyFormat {
    @JvmStatic
    fun obj2jSONObject(obj: Any): JSONObject =
        if (obj is String)
            JSONObject(obj)
        else JSONObject(UtilGsonFormat.obj2strJson_gson(obj))

}