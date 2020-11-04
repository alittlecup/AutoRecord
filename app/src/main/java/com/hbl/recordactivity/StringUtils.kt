package com.hbl.recordactivity

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import java.io.StringReader
import java.util.regex.Pattern

object StringUtils {
    const val defRegex = "def = \\{[\\s\\S]*?\\}\\;"
    private const val hasFlagRegex = "hasFlag: ([^\\\"]+)\\,"
    const val geoApiInfoRegex = "\"geo_api_info\"\\:\"\\{[\\s\\S]*?\\}\","
    const val oldInfoRegex = "oldInfo: \\{[\\s\\S]*?\\}\\,\n"

    private fun getOldInfoJson(html: String): JsonObject {
        var old = html.findAll(oldInfoRegex).replace("oldInfo: ", "")
            .replaceAfterLast("}", "")
        var oldJsonRemoteGeo = old.replace(Regex(geoApiInfoRegex), "")
        return toJsonObject(oldJsonRemoteGeo)
    }

    private fun getOldGeoApiInfoJson(html: String): JsonObject {
        var old = html.findAll(oldInfoRegex).replace("oldInfo: ", "")
            .replaceAfterLast("}", "")
        var oldJsonRemoteGeo = old.findAll(geoApiInfoRegex).replace("\"geo_api_info\":\"", "")
            .replaceAfterLast("}", "")
        return toJsonObject(oldJsonRemoteGeo)
    }

    private fun getDefJson(html: String): JsonObject {
        var def = html.findAll(defRegex).replace("def = ", "")
            .replaceAfterLast(";}", "")
        var defJsonRemoteGeo = def.replace(Regex(geoApiInfoRegex), "")
        return toJsonObject(defJsonRemoteGeo)
    }

    public fun generatePostJson(html: String): JsonObject {
        var defJson = getDefJson(html)
        val postJson = defJson.deepCopy()
        var oldInfoJson = getOldInfoJson(html)
        postJson.addProperty("ismoved", 0)
        postJson.addProperty("sfxk", 0)
        postJson.addProperty("xkqq", "")
        postJson.addProperty("sfyyjc", 0)
        postJson.addProperty("address", oldInfoJson.get("address").asString)
        postJson.addProperty("area", oldInfoJson.get("area").asString)
        postJson.addProperty("province", oldInfoJson.get("province").asString)
        postJson.addProperty("city", oldInfoJson.get("city").asString)
        postJson.addProperty("geo_api_info", getOldGeoApiInfoJson(html).toString())
        return postJson
    }

    public fun hasFlag(html: String): Boolean {
        var hasFlag: String = html.findAll(hasFlagRegex).replace("hasFlag: ", "").replace(",", "")
        return hasFlag == "'1'"
    }

    public fun toJsonObject(jsonString: String): JsonObject {
        return JsonParser().parse(JsonReader(StringReader(jsonString)).apply {
            isLenient = true
        }).asJsonObject
    }

    private fun String.findAll(regex: String): String {
        var matcher = Pattern.compile(regex).matcher(this)
        if (matcher.find()) {
            return matcher.group()
        }
        return ""
    }
}