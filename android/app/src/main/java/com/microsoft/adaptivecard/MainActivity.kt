package com.microsoft.adaptivecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.microsoft.adaptivecard.core.Action
import com.microsoft.adaptivecard.core.ActionSet
import com.microsoft.adaptivecard.core.AdaptiveCard
import com.microsoft.adaptivecard.core.CardElement
import com.microsoft.adaptivecard.core.ColumnSet
import com.microsoft.adaptivecard.core.Container
import com.microsoft.adaptivecard.core.FactSet
import com.microsoft.adaptivecard.core.Image
import com.microsoft.adaptivecard.core.InputChoiceSet
import com.microsoft.adaptivecard.core.InputDate
import com.microsoft.adaptivecard.core.InputText
import com.microsoft.adaptivecard.core.InputTime
import com.microsoft.adaptivecard.core.InputToggle
import com.microsoft.adaptivecard.core.TextBlock
import com.microsoft.adaptivecard.render.AdaptiveCardComposable
import com.microsoft.adaptivecard.ui.theme.AdaptiveCardLLMTheme
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val moshi = Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory
                    .of(Action::class.java, "type")
                    .withSubtype(Action.ActionOpenUrl::class.java, "Action.OpenUrl")
                    .withSubtype(Action.ActionSubmit::class.java, "Action.Submit")
                    .withSubtype(Action.ActionShowCard::class.java, "Action.ShowCard")
            )
            .add(
                PolymorphicJsonAdapterFactory
                    .of(CardElement::class.java, "type")
                    .withSubtype(ActionSet::class.java, "ActionSet")
                    .withSubtype(ColumnSet::class.java, "ColumnSet")
                    .withSubtype(Container::class.java, "Container")
                    .withSubtype(FactSet::class.java, "FactSet")
                    .withSubtype(Image::class.java, "Image")
                    .withSubtype(InputChoiceSet::class.java, "Input.ChoiceSet")
                    .withSubtype(InputDate::class.java, "Input.Date")
                    .withSubtype(InputText::class.java, "Input.Text")
                    .withSubtype(InputTime::class.java, "Input.Time")
                    .withSubtype(InputToggle::class.java, "Input.Toggle")
                    .withSubtype(TextBlock::class.java, "TextBlock")
            )
            .add(KotlinJsonAdapterFactory())
            .build()

        // Create a JsonAdapter for AdaptiveCard
        val jsonAdapter: JsonAdapter<AdaptiveCard> = moshi.adapter(AdaptiveCard::class.java)

        val adaptiveCardData = jsonAdapter.fromJson(json)



        setContent {
            AdaptiveCardLLMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting(adaptiveCardData?.toString() ?: "hello")
                    AdaptiveCardComposable(adaptiveCardData!!)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdaptiveCardLLMTheme {
        Greeting("Android")
    }
}