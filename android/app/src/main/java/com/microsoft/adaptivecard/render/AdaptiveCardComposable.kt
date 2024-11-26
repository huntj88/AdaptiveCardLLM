package com.microsoft.adaptivecard.render

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
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
    val state = remember { mutableStateOf(TextFieldValue()) }

    TextField(
        value = state.value,
        onValueChange = { state.value = it },
        placeholder = { inputText.placeholder?.let { Text(it) } },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun InputChoiceSetComposable(inputChoiceSet: InputChoiceSet) {
    var expanded by remember { mutableStateOf(true) }
    var selectedText by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

    Box {
        TextField(
            value = selectedText,
            readOnly = true,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            placeholder = {
                // TODO: localization
                Text("Choose an option")
            },

            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            inputChoiceSet.choices?.forEach { choice ->
                DropdownMenuItem(text = { Text(choice.title) }, onClick = {
                    selectedText = choice.title
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun InputDateComposable(inputDate: InputDate) {
    // TODO: theme/styling
    DatePickerFieldToModal(placeholder = inputDate.placeholder)
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