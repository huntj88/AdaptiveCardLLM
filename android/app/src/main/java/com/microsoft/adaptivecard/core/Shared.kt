import com.microsoft.adaptivecard.core.FontType
import com.microsoft.adaptivecard.core.HostWidth
import com.microsoft.adaptivecard.core.SizeUnit
import com.microsoft.adaptivecard.core.Spacing
import com.microsoft.adaptivecard.core.TargetWidthCondition
import com.microsoft.adaptivecard.core.TextColor
import com.microsoft.adaptivecard.core.TextSize
import com.microsoft.adaptivecard.core.TextWeight
import java.util.Date

// Imports (you'll need to adjust these based on your Kotlin project structure)
interface ILocalizableString {
    val key: String
    var defaultValue: String
}

typealias InterpolationArguments = Map<String, Any?>

interface IMarkdownProcessingResult {
    val didProcess: Boolean
    val output: Any? // Replace with appropriate Kotlin UI component type
}

// Kotlin doesn't have a direct equivalent to React's CSSProperties
// You might want to create a custom type or use a third-party library
typealias CSSProperties = Map<String, Any>

interface MandatoryStyle {
    val style: CSSProperties
}

// These would need to be replaced with appropriate Kotlin UI framework types
typealias AllHTMLAttributes = MandatoryStyle
typealias ImgHTMLAttributes = MandatoryStyle
typealias ButtonHTMLAttributes = MandatoryStyle

interface IInput {
    var id: String?
    var value: Any?
    var valueAsString: String?
    fun validateValue(): Boolean
    fun focus(): Boolean
    fun resetDirtyState()
    fun isDirty(): Boolean
    fun isSet(): Boolean
    fun resetValue()
}

interface IImage {
    val allowExpand: Boolean
    val isSelectable: Boolean
}

enum class TextBlockStyle {
    DEFAULT, HEADING, COLUMN_HEADER
}

interface ITextProperties {
    val size: TextSize?
    val weight: TextWeight?
    val color: TextColor?
    val fontType: FontType?
    val isSubtle: Boolean?
    val wrap: Boolean
    val maxLines: Int?
    val style: TextBlockStyle?
}

interface IProcessableUrl {
    val unprocessedUrl: String
    fun setProcessedUrl(value: String)
}

interface IDataQueryRequest {
    val searchString: String
    //val dataQuery: DataQuery
    val onDataQueryCompleted: (IDataQueryResponse) -> Unit
}

interface IDataQueryResponse {
    val query: String
    val data: String // Using String instead of JSON, as Kotlin doesn't have a direct JSON type
    val error: String?
}

object GlobalSettings {
    var useMarkdownInRadioButtonAndCheckbox = true
    var alwaysBleedSeparators = false
    var enableFullJsonRoundTrip = false
    var displayInputValidationErrors = true
    var allowPreProcessingPropertyValues = false
    var enableFallback = true
    var useWebkitLineClamp = true
    var allowMoreThanMaxActionsInOverflowMenu = true
    var removePaddingFromContainersWithBackgroundImage = false
    var resetInputsDirtyStateAfterActionExecution = false
    var defaultUnlocalizableStringFallback = "Undefined"
    var allowDelayedContainerRendering = true
    var useBorderColorForSeparator = false
    var failRenderingWhenUnknownPropertiesAreFound = false
    var failRenderingWhenInvalidPropertyValuesAreFound = false
    var fluentIconCdnBasePath = "https://res-1.cdn.office.net/assets/fluentui-react-icons/2.0.226/"
    var enableIconSupportForActionButtons = false
    var allowSimultaneousVideoPlayback = false
}

object ContentTypes {
    const val applicationJson = "application/json"
    const val applicationXWwwFormUrlencoded = "application/x-www-form-urlencoded"
}

interface ISeparationDefinition {
    val spacing: Int
    val lineThickness: Int?
    val lineColor: String?
}

typealias Dictionary<T> = Map<String, T>

interface ISpacingDefinition {
    var left: Int
    var top: Int
    var right: Int
    var bottom: Int
}

data class SpacingDefinition(
    override var left: Int = 0,
    override var top: Int = 0,
    override var right: Int = 0,
    override var bottom: Int = 0
) : ISpacingDefinition

data class PaddingDefinition(
    var top: Spacing = Spacing.None,
    var right: Spacing = Spacing.None,
    var bottom: Spacing = Spacing.None,
    var left: Spacing = Spacing.None
)

val pixelSizeRegexValue = object {
    val regEx = Regex("^\\d+px$")
    val displayText = "<number>px"
}

data class SizeAndUnit(
    var physicalSize: Int,
    var unit: SizeUnit
) {
    companion object {
        fun parse(input: String, requireUnitSpecifier: Boolean = false): SizeAndUnit {
            val result = SizeAndUnit(0, SizeUnit.Weight)

            if (input.toIntOrNull() != null) {
                result.physicalSize = input.toInt()
                return result
            } else {
                val regExp = Regex("^([0-9]+)(px|\\*)?$")
                val matches = regExp.find(input)
                val expectedMatchCount = if (requireUnitSpecifier) 3 else 2

                if (matches != null && matches.groupValues.size >= expectedMatchCount) {
                    result.physicalSize = matches.groupValues[1].toInt()

                    if (matches.groupValues.size == 3) {
                        if (matches.groupValues[2] == "px") {
                            result.unit = SizeUnit.Pixel
                        }
                    }

                    return result
                }
            }

            throw IllegalArgumentException("Invalid size: $input")
        }
    }
}

fun compareHostWidths(width1: HostWidth, width2: HostWidth): Int {
    return when {
        width1 == width2 -> 0
        width1 < width2 -> -1
        else -> 1
    }
}

data class TargetWidth(
    var width: HostWidth = HostWidth.Wide,
    var condition: TargetWidthCondition? = null
) {
    companion object {
        fun parse(input: String): TargetWidth? {
            val conditions = TargetWidthCondition.values().joinToString("|") { it.name }
            val widths = HostWidth.values().joinToString("|") { it.name }
            val regEx = Regex("^(?:($conditions):)?($widths)$", RegexOption.IGNORE_CASE)
            val matches = regEx.find(input)

            if (matches != null && matches.groupValues.size >= 3) {
                val result = TargetWidth()
                val prefix = matches.groupValues[1].toLowerCase()

                if (prefix.isNotEmpty()) {
                    val condition = TargetWidthCondition.valueOf(prefix.toUpperCase())
                    result.condition = condition
                }

                val width = matches.groupValues[2].toLowerCase()
                if (width.isNotEmpty()) {
                    val hostWidth = HostWidth.valueOf(width.toUpperCase())
                    result.width = hostWidth
                    return result
                }
            }

            return null
        }
    }

    fun matches(hostWidth: HostWidth): Boolean {
        return when (condition) {
            null -> width == hostWidth
            TargetWidthCondition.AtLeast -> width <= hostWidth
            TargetWidthCondition.AtMost -> hostWidth <= width
        }
    }

    override fun toString(): String {
        return condition?.let { "${it.name}:${width.name}" } ?: width.name
    }
}

interface IResourceInformation {
    val url: String
    val mimeType: String
}

object UUID {
    private val lut = Array(256) { i -> if (i < 16) "0${i.toString(16)}" else i.toString(16) }

    fun generate(): String {
        val d0 = (Math.random() * 0xffffffff).toInt()
        val d1 = (Math.random() * 0xffffffff).toInt()
        val d2 = (Math.random() * 0xffffffff).toInt()
        val d3 = (Math.random() * 0xffffffff).toInt()

        return "${lut[d0 and 0xff]}${lut[(d0 shr 8) and 0xff]}${lut[(d0 shr 16) and 0xff]}${lut[(d0 shr 24) and 0xff]}-" +
                "${lut[d1 and 0xff]}${lut[(d1 shr 8) and 0xff]}-" +
                "${lut[((d1 shr 16) and 0x0f) or 0x40]}${lut[(d1 shr 24) and 0xff]}-" +
                "${lut[(d2 and 0x3f) or 0x80]}${lut[(d2 shr 8) and 0xff]}-" +
                "${lut[(d2 shr 16) and 0xff]}${lut[(d2 shr 24) and 0xff]}" +
                "${lut[d3 and 0xff]}${lut[(d3 shr 8) and 0xff]}" +
                "${lut[(d3 shr 16) and 0xff]}${lut[(d3 shr 24) and 0xff]}"
    }

    init {
        // Initialize lut array
        for (i in 0..255) {
            lut[i] = if (i < 16) "0${i.toString(16)}" else i.toString(16)
        }
    }
}

typealias RenderArgs = Map<String, Any>

interface IElementSpacings {
    val padding: ISpacingDefinition
    val margin: ISpacingDefinition
}