package com.microsoft.adaptivecard.core

object EnumUtils {
    inline fun <reified T : Enum<T>> getEnumValueByName(name: String): T? {
        return enumValues<T>().find { it.name.equals(name, ignoreCase = true) }
    }

    inline fun <reified T : Enum<T>> getEnumNames(): List<String> {
        return enumValues<T>().map { it.name }
    }
}