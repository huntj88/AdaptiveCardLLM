// HostConfig.kt
package com.microsoft.adaptivecard.core

import kotlin.reflect.KClass

inline fun <reified T : Enum<T>> parseHostConfigEnum(value: Any?, defaultValue: T): T {
    return when (value) {
        is String -> try {
            enumValueOf<T>(value.uppercase())
        } catch (e: IllegalArgumentException) {
            defaultValue
        }
        is Number -> try {
            enumValues<T>()[value.toInt()]
        } catch (e: ArrayIndexOutOfBoundsException) {
            defaultValue
        }
        else -> defaultValue
    }
}

// Basic definitions
open class ColorDefinition(
    var default: String = "#000000",
    var subtle: String = "#666666"
) {
    open fun parse(obj: Map<String, Any>?) {
        obj?.let {
            default = it["default"] as? String ?: default
            subtle = it["subtle"] as? String ?: subtle
        }
    }
}

class TextColorDefinition : ColorDefinition() {
    val highlightColors = ColorDefinition("#22000000", "#11000000")

    override fun parse(obj: Map<String, Any>?) {
        super.parse(obj)
        obj?.let {
            highlightColors.parse(it["highlightColors"] as? Map<String, Any>)
        }
    }
}

class AdaptiveCardConfig(
    var allowCustomStyle: Boolean = false
) {
    constructor(obj: Map<String, Any>?) : this() {
        obj?.let {
            allowCustomStyle = it["allowCustomStyle"] as? Boolean ?: allowCustomStyle
        }
    }
}

class ImageSetConfig(
    var imageSize: Size = Size.Medium,
    var maxImageHeight: Int = 100
) {
    companion object {
        const val defaultMaxImageHeight: Int = 100
    }

    constructor(obj: Map<String, Any>?) : this() {
        obj?.let {
            imageSize = parseHostConfigEnum(it["imageSize"], Size.Medium)
            maxImageHeight = (it["maxImageHeight"] as? Number)?.toInt() ?: defaultMaxImageHeight
        }
    }

    fun toJSON(): Map<String, Any> = mapOf(
        "imageSize" to imageSize.name,
        "maxImageHeight" to maxImageHeight
    )
}

class MediaConfig(
    var defaultPoster: String? = null,
    var useHTML5PlayerAsFallback: Boolean = false
) {
    val placeholderHeights = mapOf(
        "veryNarrow" to 120,
        "narrow" to 175,
        "standard" to 250,
        "wide" to 300
    )

    constructor(obj: Map<String, Any>?) : this() {
        obj?.let {
            defaultPoster = it["defaultPoster"] as? String
            useHTML5PlayerAsFallback = it["useHTML5PlayerAsFallback"] as? Boolean ?: useHTML5PlayerAsFallback
        }
    }

    fun toJSON(): Map<String, Any?> = mapOf(
        "defaultPoster" to defaultPoster
    )
}

open class BaseTextDefinition {
    var size: TextSize = TextSize.Default
    var color: TextColor = TextColor.Default
    var isSubtle: Boolean = false
    var weight: TextWeight = TextWeight.Default

    constructor()

    constructor(obj: Map<String, Any>?) {
        parse(obj)
    }

    open fun parse(obj: Map<String, Any>?) {
        obj?.let {
            size = parseHostConfigEnum(it["size"], TextSize.Default)
            color = parseHostConfigEnum(it["color"], TextColor.Default)
            isSubtle = it["isSubtle"] as? Boolean ?: isSubtle
            weight = parseHostConfigEnum(it["weight"], getDefaultWeight())
        }
    }

    open fun getDefaultWeight(): TextWeight = TextWeight.Default

    open fun toJSON(): Map<String, Any> = mapOf(
        "size" to size.name,
        "color" to color.name,
        "isSubtle" to isSubtle,
        "weight" to weight.name
    )
}

public class TextStyleDefinition : BaseTextDefinition {
    var fontType: FontType = FontType.Default

    constructor() : super()

    constructor(obj: Map<String, Any>?) : super(obj)

    override fun parse(obj: Map<String, Any>?) {
        super.parse(obj)
        obj?.let {
            fontType = parseHostConfigEnum(it["fontType"], FontType.Default)
        }
    }

    override fun toJSON(): Map<String, Any> {
        val json = super.toJSON().toMutableMap()
        json["fontType"] = fontType.name
        return json
    }
}

class FactTextDefinition : BaseTextDefinition {
    var wrap: Boolean = true

    constructor() : super()

    constructor(obj: Map<String, Any>?) : super(obj)

    override fun parse(obj: Map<String, Any>?) {
        super.parse(obj)
        obj?.let {
            wrap = it["wrap"] as? Boolean ?: wrap
        }
    }

    override fun toJSON(): Map<String, Any> {
        val json = super.toJSON().toMutableMap()
        json["wrap"] = wrap
        return json
    }
}

class FactTitleDefinition : FactTextDefinition {
    var maxWidth: Int? = 150
    override var weight: TextWeight = TextWeight.Bolder

    constructor() : super()

    constructor(obj: Map<String, Any>?) : super(obj) {
        obj?.let {
            maxWidth = it["maxWidth"] as? Int ?: maxWidth
            weight = parseHostConfigEnum(it["weight"], TextWeight.Bolder)
        }
    }

    override fun getDefaultWeight(): TextWeight = TextWeight.Bolder

    override fun toJSON(): Map<String, Any> {
        val json = super.toJSON().toMutableMap()
        maxWidth?.let { json["maxWidth"] = it }
        return json
    }
}