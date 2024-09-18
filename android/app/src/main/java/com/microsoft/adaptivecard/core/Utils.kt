package com.microsoft.adaptivecard.core

import UUID
import java.util.Date

// Assuming AllHTMLAttributes is defined elsewhere in your Kotlin project
typealias AllHTMLAttributes = Map<String, Any?>

fun addClass(props: MutableMap<String, Any?>, vararg classNames: String) {
    val classList = (props["className"] as? String)?.split("\\s+".toRegex()) ?: listOf()
    props["className"] = (classList + classNames).joinToString(" ")
}

fun createProps(): AllHTMLAttributes {
    return mutableMapOf("style" to mutableMapOf<String, Any?>())
}

fun isMobileOS(): Boolean {
    val userAgent = System.getProperty("http.agent") ?: ""
    return userAgent.contains("Android", ignoreCase = true) ||
            userAgent.contains("iPad", ignoreCase = true) ||
            userAgent.contains("iPhone", ignoreCase = true)
}

fun isiOS(): Boolean {
    val userAgent = System.getProperty("http.agent") ?: ""
    return userAgent.contains("iPhone", ignoreCase = true)
}

fun isAndroid(): Boolean {
    val userAgent = System.getProperty("http.agent") ?: ""
    return userAgent.contains("Android", ignoreCase = true)
}

/**
 * Generate a UUID prepended with "__ac-"
 */
fun generateUniqueId(): String {
    return "__ac-${UUID.generate()}"
}

fun appendChild(node: Any, child: Any?) {
    // This function might need to be implemented differently depending on your UI framework
    // For example, in Android:
    // if (node is ViewGroup && child is View) {
    //     node.addView(child)
    // }
}

fun parseString(obj: Any?, defaultValue: String? = null): String? {
    return when (obj) {
        is String -> obj
        else -> defaultValue
    }
}

fun parseNumber(obj: Any?, defaultValue: Number? = null): Number? {
    return when (obj) {
        is Number -> obj
        else -> defaultValue
    }
}

fun parseBool(value: Any?, defaultValue: Boolean? = null): Boolean? {
    return when (value) {
        is Boolean -> value
        is String -> when (value.toLowerCase()) {
            "true" -> true
            "false" -> false
            else -> defaultValue
        }
        else -> defaultValue
    }
}

private fun padStart(s: String, prefix: String, targetLength: Int): String {
    var result = s
    while (result.length < targetLength) {
        result = prefix + result
    }
    return result
}

fun parseDate(dateString: String): Date? {
    val regEx = "^(\\d{4})-(\\d{2})-(\\d{2})$".toRegex()
    val match = regEx.find(dateString)
    val expectedMatchCount = 4

    if (match != null && match.groupValues.size == expectedMatchCount) {
        val year = match.groupValues[1].toInt()
        val month = match.groupValues[2].toInt() - 1 // Months start from 0 in Java/Kotlin
        val day = match.groupValues[3].toInt()

        return Date(year - 1900, month, day)
    }

    return null
}

fun dateToString(date: Date): String {
    val year = date.year + 1900
    val month = padStart((date.month + 1).toString(), "0", 2)
    val day = padStart(date.date.toString(), "0", 2)

    return "$year-$month-$day"
}

inline fun <reified T : Enum<T>> parseEnum(name: String?, defaultValue: T? = null): T? {
    if (name == null) {
        return defaultValue
    }

    return enumValues<T>().find { it.name.equals(name, ignoreCase = true) } ?: defaultValue
}

fun stringToCssColor(color: String?): String? {
    if (color != null) {
        val regEx = "#([0-9A-F]{2})([0-9A-F]{2})([0-9A-F]{2})([0-9A-F]{2})?".toRegex(RegexOption.IGNORE_CASE)
        val match = regEx.find(color)

        if (match != null && match.groupValues.size == 5) {
            val a = match.groupValues[1].toInt(16) / 255.0
            val r = match.groupValues[2].toInt(16)
            val g = match.groupValues[3].toInt(16)
            val b = match.groupValues[4].toInt(16)

            return "rgba($r,$g,$b,$a)"
        }
    }

    return color
}

fun clearElementChildren(element: Any) {
    // This function might need to be implemented differently depending on your UI framework
    // For example, in Android:
    // if (element is ViewGroup) {
    //     element.removeAllViews()
    // }
}

fun addCancelSelectActionEventHandler(element: Any) {
    // This function might need to be implemented differently depending on your UI framework
    // For example, in Android:
    // if (element is View) {
    //     element.setOnClickListener { event ->
    //         event.preventDefault()
    //         event.stopPropagation()
    //     }
    // }
}

fun interpolateString(value: String, args: Map<String, Any?> = emptyMap()): String {
    var result = value
    args.forEach { (key, arg) ->
        val replaceWith = arg?.toString() ?: ""
        result = result.replace("{{$key}}", replaceWith)
    }
    return result
}