package io.tbib.tcomposemediapicker


import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun FilePickerUI(
    onMediaPicked: (Uri) -> Unit,
    mimeType: MimeType,
    maxSize: Int? = 20 * 1024 * 1024,
    inputFieldColors: TextFieldColors = TextFieldDefaults.colors(),
    shape: CornerBasedShape,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    value: String,
    label: @Composable() (() -> Unit)? = null,
    placeholder: @Composable() (() -> Unit)? = null,
    enable: Boolean = true,
    supportingText: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    prefix: @Composable() (() -> Unit)? = null,
    suffix: @Composable() (() -> Unit)? = null,

    ) {

    val picker = TMediaPicker.PickSingleMedia().apply {
        Init(
            maxSize = maxSize,
            mimeType= mimeType,
            onMediaPicked = { uri ->
                onMediaPicked(uri)

        })
    }

    OutlinedTextField(
        enabled = enable,
        supportingText = supportingText,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,

       modifier = modifier,
        shape = shape,
        readOnly = true,
        colors = inputFieldColors,
        value = value,
        label = label,
        placeholder = placeholder,
        onValueChange = { },
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Release) {
                            picker.pickMedia()
                        }
                    }
                }
            }
    )

}




@Composable
fun MultiFilePickerUI(
    onMediaPicked: (List<Uri>) -> Unit,
    mimeType: MimeType,
    inputFieldColors: TextFieldColors = TextFieldDefaults.colors(),
    shape: CornerBasedShape,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    value: String,
    label : @Composable ()->Unit = { },
    placeholder : @Composable ()->Unit = { }


    ) {
    val picker = TMediaPicker.PickMultiMedia().apply {
        Init(
            mimeType= mimeType,
            onMediaPicked = { uri ->
                onMediaPicked(uri)
            })
    }

    OutlinedTextField(
        modifier = modifier,
        shape = shape,
        readOnly = true,
        label = label,
        placeholder = placeholder,
        colors = inputFieldColors,
        value =value,
        onValueChange = { },
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Release) {
                            picker.pickMedia()
                        }
                    }
                }
            }
    )

}