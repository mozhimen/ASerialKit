package com.mozhimen.serialk.gson

import org.json.JSONArray

/**
 * @ClassName UtilKStrJson
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/10 0:33
 * @Version 1.0
 */
fun <T> String.strJson2ts(clazz: Class<T>): ArrayList<T?>? =
    UtilStrJson.strJson2ts(this, clazz)

///////////////////////////////////////////////////////////////////////////////

object UtilStrJson {
    @JvmStatic
    fun <T> strJson2ts(strJson: String, clazz: Class<T>): ArrayList<T?>? =
        UtilJSONArrayFormat.jSONArray2ts(JSONArray(strJson.trim { strJson <= " " }), clazz)

}