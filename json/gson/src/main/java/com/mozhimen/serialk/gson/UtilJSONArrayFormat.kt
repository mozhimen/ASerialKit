package com.mozhimen.serialk.gson

import com.mozhimen.kotlin.utilk.android.util.e
import com.mozhimen.kotlin.utilk.commons.IUtilK
import org.json.JSONArray
import org.json.JSONObject

/**
 * @ClassName UtilKJSONArrayFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/6/9 22:08
 * @Version 1.0
 */

fun <T> JSONArray.jSONArray2ts(clazz: Class<T>): ArrayList<T?>? =
    UtilJSONArrayFormat.jSONArray2ts(this, clazz)

////////////////////////////////////////////////

object UtilJSONArrayFormat : IUtilK {
    @JvmStatic
    fun <T> jSONArray2ts(jsonArray: JSONArray, clazz: Class<T>): ArrayList<T?>? {
        val strs = ArrayList<T?>()
        try {
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObj = jsonArray[i] as? JSONObject?
                if (jsonObj != null)
                    strs.add(UtilGsonFormat.strJson2t_gson(jsonObj.toString(), clazz))
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