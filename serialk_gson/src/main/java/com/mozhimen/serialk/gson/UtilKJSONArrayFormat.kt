package com.mozhimen.serialk.gson

import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.commons.IUtilK
import org.json.JSONArray
import org.json.JSONObject

/**
 * @ClassName UtilKJSONArrayFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/9 22:08
 * @Version 1.0
 */

fun <T> JSONArray.jSONArray2tList(clazz: Class<T>): ArrayList<T?>? =
    UtilKJSONArrayFormat.jSONArray2tList(this, clazz)

////////////////////////////////////////////////

object UtilKJSONArrayFormat : IUtilK {
    @JvmStatic
    fun <T> jSONArray2tList(jsonArray: JSONArray, clazz: Class<T>): ArrayList<T?>? {
        val strs = ArrayList<T?>()
        try {
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObj = jsonArray[i] as? JSONObject?
                if (jsonObj != null)
                    strs.add(UtilKGsonWrapper.strJson2t_ofGson(jsonObj.toString(), clazz))
                else
                    strs.add(null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
            return null
        }
        return strs
    }
}