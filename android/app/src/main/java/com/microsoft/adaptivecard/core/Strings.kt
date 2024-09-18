package com.microsoft.adaptivecard.core

import java.util.*

object Strings {
    object errors {
        fun unknownElementType(typeName: String) =
            "Unknown element type \"$typeName\". Fallback will be used if present."

        fun unknownActionType(typeName: String) =
            "Unknown action type \"$typeName\". Fallback will be used if present."

        fun elementTypeNotAllowed(typeName: String) =
            "Element type \"$typeName\" is not allowed in this context."

        fun actionTypeNotAllowed(typeName: String) =
            "Action type \"$typeName\" is not allowed in this context."

        fun invalidPropertyValue(value: Any, propertyName: String) =
            "Invalid value \"$value\" for property \"$propertyName\"."

        fun showCardMustHaveCard() =
            "An Action.ShowCard must have its \"card\" property set to a valid AdaptiveCard object."

        fun invalidColumnWidth(invalidWidth: String) =
            "Invalid column width \"$invalidWidth\" - defaulting to \"auto\"."

        fun invalidCardVersion(defaultingToVersion: String) =
            "Invalid card version. Defaulting to \"$defaultingToVersion\"."

        fun invalidVersionString(versionString: String) =
            "Invalid version string \"$versionString\"."

        fun propertyValueNotSupported(
            value: Any,
            propertyName: String,
            supportedInVersion: String,
            versionUsed: String
        ) = "Value \"$value\" for property \"$propertyName\" is supported in version $supportedInVersion, but you are using version $versionUsed."

        fun propertyNotSupported(
            propertyName: String,
            supportedInVersion: String,
            versionUsed: String
        ) = "Property \"$propertyName\" is supported in version $supportedInVersion, but you are using version $versionUsed."

        fun indexOutOfRange(index: Int) = "Index out of range ($index)."

        fun elementCannotBeUsedAsInline() =
            "RichTextBlock.addInline: the specified card element cannot be used as a RichTextBlock inline."

        fun inlineAlreadyParented() =
            "RichTextBlock.addInline: the specified inline already belongs to another RichTextBlock."

        fun interactivityNotAllowed() = "Interactivity is not allowed."

        fun inputsMustHaveUniqueId() = "All inputs must have a unique Id."

        fun choiceSetMustHaveAtLeastOneChoice() =
            "An Input.ChoiceSet must have at least one choice defined."

        fun choiceSetChoicesMustHaveTitleAndValue() =
            "All choices in an Input.ChoiceSet must have their title and value properties set."

        fun propertyMustBeSet(propertyName: String) =
            "Property \"$propertyName\" must be set."

        fun actionHttpHeadersMustHaveNameAndValue() =
            "All headers of an Action.Http must have their name and value properties set."

        fun tooManyActions(maximumActions: Int) =
            "Maximum number of actions exceeded ($maximumActions)."

        fun tooLittleTimeDelay(minAutoplayDelay: Int) =
            "Autoplay Delay is too short ($minAutoplayDelay)."

        fun columnAlreadyBelongsToAnotherSet() =
            "This column already belongs to another ColumnSet."

        fun invalidCardType() =
            "Invalid or missing card type. Make sure the card's type property is set to \"AdaptiveCard\"."

        fun unsupportedCardVersion(version: String, maxSupportedVersion: String) =
            "The specified card version ($version) is not supported or still in preview. The latest released card version is $maxSupportedVersion."

        fun duplicateId(id: String) = "Duplicate Id \"$id\"."

        fun markdownProcessingNotEnabled() =
            "Markdown processing isn't enabled. Please see https://www.npmjs.com/package/adaptivecards#supporting-markdown"

        fun elementAlreadyParented() =
            "The element already belongs to another container."

        fun actionAlreadyParented() =
            "The action already belongs to another element."

        fun elementTypeNotStandalone(typeName: String) =
            "Elements of type $typeName cannot be used as standalone elements."

        fun unknownProperty(propertyName: String) =
            "Unknown property \"$propertyName\""
    }

    object hints {
        fun dontUseWeightedAndStretchedColumnsInSameSet() =
            "It is not recommended to use weighted and stretched columns in the same ColumnSet, because in such a situation stretched columns will always get the minimum amount of space."
    }

    val overflowButtonText = LocalizableString("overflowButtonText", "...")
    val overflowButtonTooltip = LocalizableString("overflowButtonTooltip", "More options")
    val mediaPlayerAriaLabel = LocalizableString("mediaPlayerAriaLabel", "Media content")
    val mediaPlayerPlayMedia = LocalizableString("mediaPlayerPlayMedia", "Play media")
    val youTubeVideoPlayer = LocalizableString("youTubeVideoPlayer", "YouTube video player")
    val vimeoVideoPlayer = LocalizableString("vimeoVideoPlayer", "Vimeo video player")
    val dailymotionVideoPlayer = LocalizableString("dailymotionVideoPlayer", "Dailymotion video player")
    val mediaPlayerPosterAltText = LocalizableString("mediaPlayerPosterAltText", "Video poster")
    val emptyElementText = LocalizableString("emptyElementText", "Empty {{elementType}}")
}

data class LocalizableString(val key: String, val defaultValue: String)