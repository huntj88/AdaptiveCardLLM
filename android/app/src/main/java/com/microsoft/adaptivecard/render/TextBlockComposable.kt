package com.microsoft.adaptivecard.render

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.microsoft.adaptivecard.core.Action
import com.microsoft.adaptivecard.core.ActionSet
import com.microsoft.adaptivecard.core.AdaptiveCard
import com.microsoft.adaptivecard.core.CardElement
import com.microsoft.adaptivecard.core.ColumnSet
import com.microsoft.adaptivecard.core.Container
import com.microsoft.adaptivecard.core.FactSet
import com.microsoft.adaptivecard.core.FontSize
import com.microsoft.adaptivecard.core.HorizontalAlignment
import com.microsoft.adaptivecard.core.Image
import com.microsoft.adaptivecard.core.InputChoiceSet
import com.microsoft.adaptivecard.core.InputDate
import com.microsoft.adaptivecard.core.InputText
import com.microsoft.adaptivecard.core.InputTime
import com.microsoft.adaptivecard.core.InputToggle
import com.microsoft.adaptivecard.core.TextBlock
import com.microsoft.adaptivecard.core.TextColor
import com.microsoft.adaptivecard.core.TextWeight

@Composable
fun factory(cardElement: CardElement) {
    return when (cardElement) {
        is TextBlock -> TextBlockComposable(cardElement)
        is Image -> ImageComposable(cardElement)
        is ActionSet -> ActionSetComposable(cardElement)
        is ColumnSet -> ColumnSetComposable(cardElement)
        is Container -> ContainerComposable(cardElement)
        is FactSet -> FactSetComposable(cardElement)
        is InputChoiceSet -> InputChoiceSetComposable(cardElement)
        is InputDate -> InputDateComposable(cardElement)
        is InputText -> InputTextComposable(cardElement)
        is InputTime -> InputTimeComposable(cardElement)
        is InputToggle -> InputToggleComposable(cardElement)
    }
}

@Composable
fun AdaptiveCardComposable(adaptiveCard: AdaptiveCard) {
    Column(Modifier.padding(top = 64.dp)) {
        adaptiveCard.body.forEach {
            factory(it)
        }
    }
}

@Composable
fun TextBlockComposable(textBlock: TextBlock) {
    Text(
        text = textBlock.text,
        color = when (textBlock.color) {
            TextColor.Default,
            TextColor.Dark,
            TextColor.Light -> MaterialTheme.colorScheme.onPrimary

            TextColor.Accent -> MaterialTheme.colorScheme.onSecondary
            TextColor.Good -> Color.Green
            TextColor.Warning -> Color.Yellow
            TextColor.Attention -> Color.Red
            null -> Color.Unspecified
        },
        textAlign = when (textBlock.horizontalAlignment) {
            HorizontalAlignment.Center -> TextAlign.Center
            HorizontalAlignment.Right -> TextAlign.Right
            else -> TextAlign.Left
        },
        modifier = Modifier.fillMaxWidth(),
        fontSize = when (textBlock.size) {
            null, FontSize.Default -> 14.sp
            FontSize.Small -> 12.sp
            FontSize.Medium -> 16.sp
            FontSize.Large -> 20.sp
            FontSize.ExtraLarge -> 24.sp
        },
        fontWeight = when (textBlock.weight) {
            TextWeight.Bolder -> FontWeight.Bold
            TextWeight.Lighter -> FontWeight.Light
            else -> FontWeight.Normal
        }
    )
}

@Composable
fun ImageComposable(image: Image) {
    // Assuming you have an ImageLoader to load images
    Image(
        painter = rememberAsyncImagePainter(image.url),
        contentDescription = image.altText,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ContainerComposable(container: Container) {
    Column {
        container.items.forEach { item ->
            factory(item)
        }
    }
}

@Composable
fun ColumnSetComposable(columnSet: ColumnSet) {
    Row {
        columnSet.columns.forEach { column ->
            Column(modifier = Modifier.weight(1f)) {
                column.items.forEach { item ->
                    factory(item)
                }
            }
        }
    }
}

@Composable
fun InputTextComposable(inputText: InputText) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { inputText.placeholder?.let { Text(it) } },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun InputChoiceSetComposable(inputChoiceSet: InputChoiceSet) {
    Column {
        inputChoiceSet.placeholder?.let { Text(text = it) }
        inputChoiceSet.choices?.forEach { choice ->
            Row {
                RadioButton(
                    selected = false,
                    onClick = {}
                )
                Text(text = choice.title)
            }
        }
    }
}

@Composable
fun InputDateComposable(inputDate: InputDate) {
    // Placeholder for date picker
    Text(text = inputDate.placeholder)
}

@Composable
fun InputTimeComposable(inputTime: InputTime) {
    // Placeholder for time picker
    Text(text = inputTime.placeholder)
}

@Composable
fun ActionSetComposable(actionSet: ActionSet) {
    Column {
        actionSet.actions.forEach { action ->
            when (action) {
                is Action.ActionOpenUrl -> Button(onClick = { /* Open URL */ }) {
                    Text(text = action.title)
                }

                is Action.ActionSubmit -> Button(onClick = { /* Submit action */ }) {
                    Text(text = action.title)
                }

                is Action.ActionShowCard -> Button(onClick = { /* Show card */ }) {
                    Text(text = action.title)
                }

                is Action.ActionToggleVisibility -> Button(onClick = { /* Show card */ }) {
                    Text(text = action.type)
                }
            }
        }
    }
}

@Composable
fun FactSetComposable(factSet: FactSet) {
    Column {
        factSet.facts.forEach { fact ->
            Row {
                Text(text = fact.title)
                Text(text = fact.value)
            }
        }
    }
}

@Composable
fun InputToggleComposable(inputToggle: InputToggle) {
    Row {
        Text(text = inputToggle.title)
        Switch(
            checked = inputToggle.isChecked ?: false,
            onCheckedChange = {}
        )
    }
}