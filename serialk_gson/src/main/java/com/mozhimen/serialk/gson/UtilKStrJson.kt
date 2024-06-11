package com.mozhimen.serialk.gson

import org.json.JSONArray

/**
 * @ClassName UtilKStrJson
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/10 0:33
 * @Version 1.0
 */
fun <T> String.strJson2tList(clazz: Class<T>): ArrayList<T?>? =
    UtilKStrJson.strJson2tList(this, clazz)

///////////////////////////////////////////////////////////////////////////////

object UtilKStrJson {
    @JvmStatic
    fun <T> strJson2tList(strJson: String, clazz: Class<T>): ArrayList<T?>? =
        UtilKJSONArrayFormat.jSONArray2tList(JSONArray(strJson.trim { strJson <= " " }), clazz)

}