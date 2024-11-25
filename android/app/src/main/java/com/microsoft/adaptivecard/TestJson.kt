package com.microsoft.adaptivecard

val json = """
{
  "schema": "https://adaptivecards.io/schemas/adaptive-card.json",
  "type": "AdaptiveCard",
  "version": "1.5",
  "body": [
    {
      "type": "TextBlock",
      "text": "Complex Adaptive Card Example",
      "size": "Large",
      "weight": "Bolder",
      "horizontalAlignment": "Center",
      "color": "Accent"
    },
    {
      "type": "Image",
      "url": "https://adaptivecards.io/content/adaptive-card-50.png",
      "altText": "Adaptive Card Logo",
      "size": "Medium",
      "horizontalAlignment": "Center"
    },
    {
      "type": "TextBlock",
      "text": "This is a sample card demonstrating multiple features of Adaptive Cards.",
      "wrap": true,
      "horizontalAlignment": "Center",
      "size": "Medium"
    },
    {
      "type": "Container",
      "style": "emphasis",
      "items": [
        {
          "type": "TextBlock",
          "text": "Container with Emphasis Style",
          "weight": "Bolder",
          "size": "Medium",
          "wrap": true
        },
        {
          "type": "TextBlock",
          "text": "This container has the emphasis style to provide more visual separation."
        }
      ]
    },
    {
      "type": "ColumnSet",
      "columns": [
        {
          "type": "Column",
          "width": "auto",
          "items": [
            {
              "type": "TextBlock",
              "text": "Column 1",
              "size": "Medium",
              "weight": "Bolder"
            },
            {
              "type": "TextBlock",
              "text": "This is the first column with some content."
            }
          ]
        },
        {
          "type": "Column",
          "width": "stretch",
          "items": [
            {
              "type": "TextBlock",
              "text": "Column 2",
              "size": "Medium",
              "weight": "Bolder"
            },
            {
              "type": "TextBlock",
              "text": "This is the second column with more content. It has more space."
            }
          ]
        }
      ]
    },
    {
      "type": "Input.Text",
      "id": "textInput",
      "placeholder": "Enter your name",
      "isMultiline": false
    },
    {
      "type": "Input.ChoiceSet",
      "id": "choiceInput",
      "style": "compact",
      "choices": [
        {
          "title": "Choice 1",
          "value": "choice1"
        },
        {
          "title": "Choice 2",
          "value": "choice2"
        },
        {
          "title": "Choice 3",
          "value": "choice3"
        }
      ],
      "placeholder": "Choose an option"
    },
    {
      "type": "Input.Date",
      "id": "dateInput",
      "placeholder": "Pick a date"
    },
    {
      "type": "Input.Time",
      "id": "timeInput",
      "placeholder": "Pick a time"
    },
    {
      "type": "TextBlock",
      "text": "Additional Information",
      "weight": "Bolder",
      "size": "Medium"
    },
    {
      "type": "FactSet",
      "facts": [
        {
          "title": "Fact 1",
          "value": "This is the first fact."
        },
        {
          "title": "Fact 2",
          "value": "This is the second fact."
        },
        {
          "title": "Fact 3",
          "value": "This is the third fact."
        }
      ]
    },
    {
      "type": "TextBlock",
      "text": "Adaptive Card Actions",
      "weight": "Bolder",
      "size": "Medium"
    },
    {
      "type": "ActionSet",
      "actions": [
        {
          "type": "Action.OpenUrl",
          "title": "Open Website",
          "url": "https://adaptivecards.io"
        },
        {
          "type": "Action.Submit",
          "title": "Submit",
          "data": {
            "action": "submitForm"
          }
        },
        {
          "type": "Action.ShowCard",
          "title": "Show More Options",
          "card": {
            "type": "AdaptiveCard",
            "version": "1.5",
            "body": [
              {
                "type": "TextBlock",
                "text": "More options to choose from",
                "weight": "Bolder",
                "size": "Medium"
              },
              {
                "type": "Input.Toggle",
                "title": "Enable Feature",
                "id": "enableFeature",
                "valueOn": "true",
                "valueOff": "false",
                "isChecked": false
              }
            ]
          }
        }
      ]
    }
  ]
}

""".trimIndent()