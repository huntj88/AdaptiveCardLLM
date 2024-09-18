import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.util.*

abstract class AbstractTextFormatter(private val regularExpression: Regex) {
    protected abstract fun internalFormat(lang: String?, matches: MatchResult): String

    fun format(lang: String?, input: String?): String? {
        if (input == null) return null

        var result = input
        val matches = regularExpression.findAll(input)
        for (match in matches) {
            result = result?.replace(match.value, internalFormat(lang, match))
        }

        return result
    }
}

@RequiresApi(Build.VERSION_CODES.N)
class DateFormatter : AbstractTextFormatter(
    Regex("""\{\{DATE\((\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:Z|(?:(?:-|\+)\d{2}:\d{2})))(?:, ?(COMPACT|LONG|SHORT))?\)\}\}""")
) {
    override fun internalFormat(lang: String?, matches: MatchResult): String {
        val date = try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).parse(matches.groupValues[1])
        } catch (e: ParseException) {
            return matches.value // Return original string if parsing fails
        }

        val format = matches.groupValues[2].lowercase().takeIf { it.isNotEmpty() } ?: "compact"

        return if (format != "compact") {
            val dateFormat = DateFormat.getDateInstance(
                when (format) {
                    "long" -> DateFormat.LONG
                    "short" -> DateFormat.SHORT
                    else -> DateFormat.DEFAULT
                },
                lang?.let { Locale(it) } ?: Locale.getDefault()
            )
            dateFormat.format(date)
        } else {
            DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault()).format(date)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
class TimeFormatter : AbstractTextFormatter(
    Regex("""\{\{TIME\((\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:Z|(?:(?:-|\+)\d{2}:\d{2})))\)\}\}""")
) {
    override fun internalFormat(lang: String?, matches: MatchResult): String {
        val date = try {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault()).parse(matches.groupValues[1])
        } catch (e: ParseException) {
            return matches.value // Return original string if parsing fails
        }

        val timeFormat = DateFormat.getTimeInstance(
            DateFormat.SHORT,
            lang?.let { Locale(it) } ?: Locale.getDefault()
        )
        return timeFormat.format(date)
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun formatText(lang: String?, text: String?): String? {
    val formatters = listOf(
        DateFormatter(),
        TimeFormatter()
    )

    var result = text
    for (formatter in formatters) {
        result = formatter.format(lang, result)
    }

    return result
}