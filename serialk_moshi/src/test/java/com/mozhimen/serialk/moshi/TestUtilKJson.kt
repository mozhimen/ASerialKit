package com.mozhimen.serialk.moshi

import com.mozhimen.kotlin.utilk.kotlin.printlog
import com.mozhimen.serialk.moshi.strJson2t_ofMoshi
import com.mozhimen.serialk.moshi.t2strJson_ofMoshi
import org.junit.Test

/**
 * @ClassName UtilKJson
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/8 0:47
 * @Version 1.0
 */
class TestUtilKJson {
    @Test
    fun test() {
        """
            {
              "user": "DK234455",
              "pwd": "78e3396c576b4420b31acd20e412c5d9"
            }
        """.trimIndent().strJson2t_ofMoshi<Bean>().printlog()

        Bean("123", "123").t2strJson_ofMoshi().printlog()
    }

    data class Bean(
        val user: String,
        val pwd: String
    )
}