package com.mozhimen.serialk.kotlinx_serialization

/**
 * @ClassName UtilJsonFormat
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/3/19
 * @Version 1.0
 */
object UtilJsonFormat {
    @JvmStatic
    inline fun <reified T> t2strJson_ks(value: T): String =
        UtilJson.encodeToString(value)

    @JvmStatic
    inline fun <reified T> strJson2t_ks(strJson: String): T =
        UtilJson.decodeFromString(strJson)
}