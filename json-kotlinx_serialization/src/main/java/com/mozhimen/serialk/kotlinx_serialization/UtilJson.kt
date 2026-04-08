package com.mozhimen.serialk.kotlinx_serialization

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * @ClassName UtilSerialization
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/3/19
 * @Version 1.0
 */
object UtilJson {
    @JvmStatic
    inline fun <reified T> encodeToString(value: T): String =
        Json.encodeToString(value)

    @JvmStatic
    inline fun <reified T> decodeFromString(strJson: String): T =
        Json.decodeFromString(strJson)
}