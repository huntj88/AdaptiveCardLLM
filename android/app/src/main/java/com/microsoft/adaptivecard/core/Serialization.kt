package com.microsoft.adaptivecard.core

import TextBlockStyle
import com.squareup.moshi.JsonClass

// Main Adaptive Card class
@JsonClass(generateAdapter = true)
data class AdaptiveCard(
    val type: String,
    val version: String,
    val body: List<CardElement>
)

// Sealed class to represent various elements in the card body
@JsonClass(generateAdapter = true)
sealed class CardElement

// TextBlock element
@JsonClass(generateAdapter = true)
data class TextBlock(
    val type: String = "TextBlock",               // Fixed to "TextBlock"
    val text: String,                            // The text content of the TextBlock
    val color: TextColor? = null,                // The color of the text (e.g., "Accent", "Good", etc.)
    val fontType: FontType? = null,                // The font type for the text (e.g., "Monospace")
    val horizontalAlignment: HorizontalAlignment? = null,  // Horizontal alignment (e.g., "Left", "Center", etc.)
    val isSubtle: Boolean? = null,               // Whether the text should appear subtle (faded) (true/false)
    val maxLines: Int? = null,                   // The maximum number of lines to display (if wrapping)
    val size: FontSize? = null,                    // The size of the text (e.g., "Small", "Medium", "Large", etc.)
    val weight: TextWeight? = null,              // The weight of the text (e.g., "Bolder", "Lighter", etc.)
    val wrap: Boolean? = null,                   // Whether text should wrap within the container (true/false)
    val style: TextBlockStyle? = null, // Optional: Controls how the TextBlock is displayed
) : CardElement()

// Image element
@JsonClass(generateAdapter = true)
data class Image(
    val type: String = "Image", // Type is always "Image"
    val url: String, // The URL to the image (supports data URI in version 1.2+)
    val altText: String? = null, // Optional: Alternate text describing the image
    val backgroundColor: String? = null, // Optional: Applies a background to a transparent image
    val height: String? = "auto", // Optional: The desired height of the image (e.g., "50px")
    val horizontalAlignment: HorizontalAlignment? = null, // Optional: Controls horizontal alignment (left, center, right)
    val selectAction: Action? = null, // Optional: Action to be invoked when the image is tapped or selected
    val size: ImageSize? = null, // Optional: Controls the approximate size of the image
    val style: ImageStyle? = null, // Optional: Controls how the image is displayed
    val width: String? = null, // Optional: The desired width of the image (e.g., "50px")

//    val fallback: FallbackOption? = null, // Optional fallback to handle unknown elements or unmet requirements
    val separator: Boolean? = null, // Optional: if true, draw a separating line at the top of the element
    val spacing: Spacing? = null, // Optional: controls the amount of spacing between this element and the previous one
    val id: String? = null, // Optional: a unique identifier for this item
    val isVisible: Boolean = true, // Optional: determines if this item is visible. Default is true
    val requires: Map<String, String>? = null // Optional: specifies the required features and their corresponding versions
) : CardElement()

// Container element (contains other elements)
@JsonClass(generateAdapter = true)
data class Container(
    val type: String = "Container", // Type is always "Container"
    val items: List<CardElement>, // List of CardElement items inside the container (e.g., TextBlock, Image, etc.)
    val selectAction: Action? = null, // Optional: Action to be invoked when the container is tapped or selected
    val style: ContainerStyle? = null, // Optional: Style hint for the container (e.g., "default", "emphasis")
    val verticalContentAlignment: String? = null, // Optional: Vertical alignment of content within the container (e.g., "top", "center", "bottom")
    val bleed: Boolean? = null, // Optional: Determines if the container should bleed through its parent's padding
    val backgroundImage: String? = null, // Optional: Background image URI (e.g., PNG, JPEG, GIF)
    val minHeight: String? = null, // Optional: Minimum height of the container (e.g., "80px")
    val rtl: Boolean? = null, // Optional: Indicates whether content should be presented right-to-left (true) or left-to-right (false)

//    val fallback: Any? = null, // Can be an Element or FallbackOption
    val height: String? = null, // Specifies the height (e.g., "100px", "auto")
    val separator: Boolean? = null, // Optional: Draw a separating line at the top of the element
    val spacing: String? = null, // Optional: Spacing between this element and the preceding element
    val id: String? = null, // Optional: A unique identifier for the element
    val isVisible: Boolean = true, // Optional: If false, this element is hidden
    val requires: Map<String, String>? = null // Optional: Key/Value pairs specifying required features with minimum versions
) : CardElement()

// ColumnSet element (contains columns)
@JsonClass(generateAdapter = true)
data class ColumnSet(
    val type: String = "ColumnSet", // Type is always "ColumnSet"
    val columns: List<Column>, // List of columns (each column contains CardElement items)
    val selectAction: Action? = null, // Optional select action (e.g., action when clicked)
    val style: ContainerStyle? = null,
    val bleed: Boolean? = null, // Optional bleed flag,
    val minHeight: String? = null, // Optional minimum height
    val horizontalAlignment: HorizontalAlignment? = null, // Horizontal alignment: left, center, right

//    val fallback: Any? = null, // Can be an Element or FallbackOption
    val height: String? = null, // Specifies the height (e.g., "100px", "auto")
    val separator: Boolean? = null, // Optional: Draw a separating line at the top of the element
    val spacing: String? = null, // Optional: Spacing between this element and the preceding element
    val id: String? = null, // Optional: A unique identifier for the element
    val isVisible: Boolean = true, // Optional: If false, this element is hidden
    val requires: Map<String, String>? = null // Optional: Key/Value pairs specifying required features with minimum versions
) : CardElement()

// Column element within ColumnSet
@JsonClass(generateAdapter = true)
data class Column(
    val type: String = "Column", // Type is always "Column"
    val items: List<CardElement> = emptyList(), // List of CardElements inside the column (e.g., TextBlock, Image, etc.)
    val backgroundImage: String? = null, // Optional: Background image URI (PNG, JPEG, GIF)
    val bleed: Boolean? = null, // Optional: Determines if the column should bleed through its parent’s padding
//    val fallback: Any? = null, // Optional: Describes what to do when an unknown item is encountered or when the requirements can't be met
    val minHeight: String? = null, // Optional: Specifies the minimum height of the column (e.g., "80px")
    val rtl: Boolean? = null, // Optional: When true, content in this column should be presented right-to-left
    val separator: Boolean? = null, // Optional: When true, draw a separating line between this column and the previous column
    val spacing: String? = null, // Optional: Controls the spacing between this column and the preceding column
    val selectAction: Action? = null, // Optional: Action that will be invoked when the column is tapped or selected
    val style: String? = null, // Optional: Style hint for the column
    val verticalContentAlignment: String? = null, // Optional: Defines the vertical alignment of content within the column
    val width: String? = null, // Optional: Specifies the width of the column (e.g., "50px", "auto", "stretch")

    val id: String? = null, // Optional: A unique identifier for the element
    val isVisible: Boolean = true, // Optional: If false, this element is hidden
    val requires: Map<String, String>? = null // Optional: Key/Value pairs specifying required features with minimum versions
)


// Input elements
@JsonClass(generateAdapter = true)
data class InputText(
    val type: String = "Input.Text", // Type is always "Input.Text"
    val id: String, // Unique identifier for the value, used to identify collected input
    val isMultiline: Boolean? = null, // If true, allows multiple lines of input
    val maxLength: Int? = null, // Maximum length of characters to collect
    val placeholder: String? = null, // Placeholder text to show when no input is provided
    val regex: String? = null, // Regular expression to enforce input format
    val style: TextInputStyle? = null, // Style hint for the input field
    val inlineAction: Action? = null, // Inline action to display beside the input
    val value: String? = null, // Initial value for the input field


    val errorMessage: String? = null, // Error message to display when input is invalid
    val isRequired: Boolean? = null, // Whether this input is required
    val label: String? = null, // Label for the input field
    val labelPosition: String? = null, // Position of the label: 'inline' or 'above' (only supported in JavaScript SDK)
    val labelWidth: String? = null, // Width of the label, either in percentage or pixels (only supported in JavaScript SDK)
    val inputStyle: String? = null, // Style hint for the input field (like read-only until focused)
//    val fallback: Any? = null, // What to do when an unknown element or feature is encountered
    val height: String? = null, // Height of the element (e.g., "100px", "auto")
    val separator: Boolean? = null, // If true, draw a separating line at the top of the element
    val spacing: String? = null, // Amount of spacing between this element and the previous one
    val isVisible: Boolean = true, // If false, this item will be removed from the visual tree
    val requires: Map<String, String>? = null // Key/Value pairs for required features with minimum version
) : CardElement()

@JsonClass(generateAdapter = true)
data class DataQuery(
    val dataset: String,  // The dataset to be queried to get the choices
    val count: Int? = null, // The maximum number of choices to be returned (optional)
    val skip: Int? = null  // The number of choices to be skipped (optional)
)

@JsonClass(generateAdapter = true)
data class InputChoiceSet(
    val type: String = "Input.ChoiceSet", // Type is always "Input.ChoiceSet"
    val id: String, // Unique identifier for the value, used to identify collected input
    val choices: List<InputChoice>? = null, // Choice options (Input.Choice objects)
    val choicesData: DataQuery? = null, // Allows dynamic fetching of choices for display in the dropdown
    val isMultiSelect: Boolean? = null, // If true, allow multiple choices to be selected
    val style: ChoiceInputStyle? = null, // Style for the choice input
    val value: String? = null, // The initial choice or set of choices that should be selected (for multi-select, a comma-separated string)
    val placeholder: String? = null, // Placeholder text displayed when no selection is made
    val wrap: Boolean? = null, // If true, allow text to wrap, otherwise text is clipped

    // Inherited properties
    val errorMessage: String? = null, // Error message when the input is invalid
    val isRequired: Boolean? = null, // Whether the input is required
    val label: String? = null, // Label for the input
    val labelPosition: InputLabelPosition? = null, // Determines label position: 'inline' or 'above'
    val labelWidth: String? = null, // Width of the label (percent or pixel value)

    // js only
//    val inputStyle: InputStyle? = null, // Style hint for input fields

//    val fallback: FallbackOption? = null, // Describes fallback behavior when requirements aren't met
    val height: BlockElementHeight? = null, // Specifies the height of the element
    val separator: Boolean? = null, // If true, draw a separating line at the top of the element
    val spacing: Spacing? = null, // Controls the spacing between this element and the preceding one
    val isVisible: Boolean = true, // Default is true; false removes the item from the visual tree
    val requires: Map<String, String>? = null // Specifies required features with corresponding minimum versions
) : CardElement()

@JsonClass(generateAdapter = true)
data class InputChoice(
    val title: String,
    val value: String
)

@JsonClass(generateAdapter = true)
data class InputDate(
    val type: String,
    val id: String,
    val placeholder: String
) : CardElement()

@JsonClass(generateAdapter = true)
data class InputTime(
    val type: String,
    val id: String,
    val placeholder: String
) : CardElement()

// ActionSet element
@JsonClass(generateAdapter = true)
data class ActionSet(
    val type: String,
    val actions: List<Action>
) : CardElement()

// Action elements
@JsonClass(generateAdapter = true)
sealed class Action {
    data class ActionOpenUrl(
        val type: String,
        val title: String,
        val url: String
    ) : Action()

    data class ActionSubmit(
        val type: String,
        val title: String,
        val data: Map<String, Any>
    ) : Action()

    data class ActionShowCard(
        val type: String,
        val title: String,
        val card: AdaptiveCard
    ) : Action()

    data class ActionToggleVisibility(
        val type: String,
        val targetElements: List<TargetElement>,
    ): Action()
}

// FactSet element (contains a list of facts)
@JsonClass(generateAdapter = true)
data class FactSet(
    val type: String,
    val facts: List<Fact>
) : CardElement()

// Fact element (key-value pair for displaying structured information)
@JsonClass(generateAdapter = true)
data class Fact(
    val title: String,
    val value: String
)

// Input.Toggle element (represents a toggle switch for true/false values)
@JsonClass(generateAdapter = true)
data class InputToggle(
    val type: String = "Input.Toggle",  // The type of input (fixed to "Input.Toggle")
    val title: String,                 // The label displayed for the toggle
    val id: String,                    // The unique ID for this input field
    val valueOn: String,               // The value when the toggle is on (true)
    val valueOff: String,              // The value when the toggle is off (false)
    val isChecked: Boolean? = null     // The initial state of the toggle (true/false)
) : CardElement()

@JsonClass(generateAdapter = true)
data class TargetElement(
    val elementId: String, // Required: The ID of the element to toggle
    val isVisible: Boolean? = null // Optional: If true, show the target element; if false, hide it; if null, toggle visibility
)