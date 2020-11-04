package com.hbl.recordactivity

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.io.FileReader
import java.io.StringReader
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
//        var time = "Thu, 03-Dec-2020 15:55:47 GMT"
//        val formatter: DateTimeFormatter =
//            DateTimeFormatter.ofPattern("EEE, dd-MMM-yyyy HH:mm:ss zzz", Locale.ENGLISH)
//        var gson = Gson().newBuilder().setLenient().create()
        var file = File("info.html")
        var fileReader = FileReader(file)
        var readText = fileReader.readText()
        var hasFlag = StringUtils.hasFlag(readText)
        println(hasFlag)
//
//
//        var old = readText.findAll(UserService.oldInfoRegex).replace("oldInfo: ", "")
//            .replaceAfterLast("}", "")
//        var geoApiInfo = old.findAll("\"geo_api_info\"\\:\"\\{[\\s\\S]*?\\}\",")
//            .replace("\"geo_api_info\":\"", "").replaceAfterLast("}", "")
//
//        var oldJsonRemoteGeo =
//            old.replace(Regex("\"geo_api_info\"\\:\"\\{[\\s\\S]*?\\}\","), "")
//
//        val oldJson: JsonObject =
//            JsonParser().parse(JsonReader(StringReader(oldJsonRemoteGeo)).apply {
//                isLenient = true
//            }).asJsonObject
//        println("---------")
//        println(oldJson)
//        println("geo: $geoApiInfo")
//
//
//        var defRegex = readText.findAll(UserService.defRegex)
//        var defJson = defRegex.replace("def = ", "").replace(";}", "")
//        var defJsonRemoteGeo =
//            defJson.replace(Regex("\"geo_api_info\"\\:\"\\{[\\s\\S]*?\\}\","), "")
//
//        val jsonObject: JsonObject =
//            JsonParser().parse(JsonReader(StringReader(defJsonRemoteGeo)).apply {
//                isLenient = true
//            }).asJsonObject
//        jsonObject.addProperty("ismoved", 0)
//        jsonObject.addProperty("sfxk", 0)
//        jsonObject.addProperty("xkqq", "")
//        jsonObject.addProperty("sfyyjc", 0)
//        jsonObject.addProperty("address", oldJson.get("address").asString)
//        jsonObject.addProperty("area", oldJson.get("area").asString)
//        jsonObject.addProperty("province", oldJson.get("province").asString)
//        jsonObject.addProperty("city", oldJson.get("city").asString)
//        jsonObject.add("geo_api_info",  JsonParser().parse(JsonReader(StringReader(geoApiInfo)).apply {
//            isLenient = true
//        }).asJsonObject)
//        println("---------")
//
//        println(jsonObject)
    }


    private fun getRegexString(string: String, regex: String): String {
        var matcher = Pattern.compile(regex).matcher(string)
        return "regex: $regex  { find: ${matcher.find()}, data : \n ${matcher.group()}}"
    }
}
























