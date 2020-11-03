package com.hbl.recordactivity

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        var time = "Thu, 03-Dec-2020 15:55:47 GMT"
        val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy HH:mm:ss zzz", Locale.ENGLISH)
        var parse = LocalDate.parse(time, formatter)
        println(parse)
    }
}