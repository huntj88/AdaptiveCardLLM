package com.microsoft.adaptivecard.core

// For ContainerStyle, we'll use a sealed class with object instances
sealed class ContainerStyle(val value: String) {
    object Default : ContainerStyle("default")
    object Emphasis : ContainerStyle("emphasis")
    object Accent : ContainerStyle("accent")
    object Good : ContainerStyle("good")
    object Attention : ContainerStyle("attention")
    object Warning : ContainerStyle("warning")
}

// For enums with string values, we'll use enum classes with custom string values
enum class ActionStyle(val value: String) {
    Default("default"),
    Positive("positive"),
    Destructive("destructive")
}

enum class ActionMode(val value: String) {
    Primary("primary"),
    Secondary("secondary")
}

// For simple enums without custom values, we can use standard Kotlin enums
enum class Size {
    Auto, Stretch, Small, Medium, Large
}

enum class ImageSize {
    Small, Medium, Large
}

enum class IconSize {
    xxSmall, xSmall, Small, Standard, Medium, Large, xLarge, xxLarge
}

enum class IconStyle {
    Regular, Filled
}

enum class SizeUnit {
    Weight, Pixel
}

enum class TextSize {
    Small, Default, Medium, Large, ExtraLarge
}

enum class TextWeight {
    Lighter, Default, Bolder
}

enum class FontType {
    Default, Monospace
}

enum class Spacing {
    None, ExtraSmall, Small, Default, Medium, Large, ExtraLarge, Padding
}

enum class TextColor {
    Default, Dark, Light, Accent, Good, Warning, Attention
}

enum class HorizontalAlignment {
    Left, Center, Right
}

enum class VerticalAlignment {
    Top, Center, Bottom
}

enum class ActionAlignment {
    Left, Center, Right, Stretch
}

enum class ImageStyle {
    Default, Person, RoundedCorners
}

enum class ShowCardActionMode {
    Inline, Popup
}

enum class Orientation {
    Horizontal, Vertical
}

enum class FillMode {
    Cover, RepeatHorizontally, RepeatVertically, Repeat
}

enum class ActionButtonState {
    Normal, Expanded, Subdued
}

enum class ActionIconPlacement {
    LeftOfTitle, AboveTitle
}

enum class InputTextStyle {
    Text, Tel, Url, Email, Password
}

enum class ValidationPhase {
    Parse, ToJSON, Validation
}

enum class ValidationEvent {
    Hint, ActionTypeNotAllowed, CollectionCantBeEmpty, Deprecated,
    ElementTypeNotAllowed, InteractivityNotAllowed, InvalidPropertyValue,
    MissingCardType, PropertyCantBeNull, TooManyActions, UnknownActionType,
    UnknownElementType, UnsupportedCardVersion, DuplicateId, UnsupportedProperty,
    RequiredInputsShouldHaveLabel, RequiredInputsShouldHaveErrorMessage,
    Other, UnknownProperty
}

enum class TypeErrorType {
    UnknownType, ForbiddenType
}

enum class ThemeName {
    Light, Dark
}

enum class HostWidth {
    VeryNarrow, Narrow, Standard, Wide
}

enum class TargetWidthCondition {
    AtLeast, AtMost
}