package com.example.mediapicker

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mediapicker.ui.theme.MediaPickerTheme
import io.tbib.composerequestpermission.RequestPermission
import io.tbib.tcomposemediapicker.MimeType
import io.tbib.tcomposemediapicker.TMediaPicker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MediaPickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    var path by remember {
        mutableStateOf("")
    }
    val imagePermission = RequestPermission().apply {
        InitPermission(
            permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            },

            )
    }

    val videoPermission = RequestPermission().apply {
        InitPermission(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_VIDEO
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
        )
    }

    val imagePicker = TMediaPicker.PickSingleMedia().apply {
        Init(MimeType.Image.All, onMediaPicked = { uri  ->
            Log.d("MediaPicker", "Image URI: $uri")

            path = uri.path ?: ""
        })
    }

    val imageMultiPicker = TMediaPicker.PickMultiMedia().apply {
        Init(MimeType.Image.All, onMediaPicked = { uri  ->
            Log.d("MediaPicker", "Image URI: $uri")

            path = uri.first().path ?: ""
        })
    }
    val videoPicker = TMediaPicker.PickSingleMedia().apply {
        Init(MimeType.Video.All, onMediaPicked = { uri  ->
            Log.d("MediaPicker", "Image URI: $uri")
            path = uri.path ?: ""
        })
    }
   Column(
         modifier = Modifier.fillMaxSize(),
       verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally

   ) {
       Text(text = path)
       Spacer(modifier = Modifier.weight(1f))
       ElevatedButton(onClick = {
           imagePermission.requestPermission(
               onPermissionGranted = {
                   imagePicker.pickMedia()
               },
               onPermissionDenied = {
                   Log.d("Permission Request", "Permission Denied")
               },
               onPermissionFailed = {
                   Log.d("Permission Request", "Permission Failed")
               }
           )
       }) {
              Text("Pick Image")
       }
         Spacer(modifier = Modifier.weight(1f))
       ElevatedButton(onClick = {
           imagePermission.requestPermission(
               onPermissionGranted = {
                   imageMultiPicker.pickMedia()
               },
               onPermissionDenied = {
                   Log.d("Permission Request", "Permission Denied")
               },
               onPermissionFailed = {
                   Log.d("Permission Request", "Permission Failed")
               }
           )
       }) {
           Text("Pick Multi Image")
       }
       Spacer(modifier = Modifier.weight(1f))
         ElevatedButton(onClick = {
                videoPermission.requestPermission(
                    onPermissionGranted = {
                        videoPicker.pickMedia()
                    },
                    onPermissionDenied = {
                        Log.d("Permission Request", "Permission Denied")
                    },
                    onPermissionFailed = {
                        Log.d("Permission Request", "Permission Failed")
                    }
                )
            }) {
                Text("Pick Video")

         }
       Spacer(modifier = Modifier.weight(1f))
   }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MediaPickerTheme {
        Greeting()
    }
}