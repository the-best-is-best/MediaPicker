package io.tbib.tcomposemediapicker


import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class TMediaPicker {

   class PickSingleMedia{
       private lateinit var launcher: ManagedActivityResultLauncher<String, Uri?>
       private lateinit var mimeTypeUser: MimeType

       @SuppressLint("Recycle", "Range", "SuspiciousIndentation")
       @Composable
       fun Init(
           mimeType: MimeType,
           /// max size in megabytes
           maxSize: Int? = 20 * 1024 * 1024,
           onMediaPicked: (Uri) -> Unit,

           ) {
           mimeTypeUser = mimeType
           val context = LocalContext.current

           launcher =
               rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->

                   uri?.let {

                       val contentResolver = context.contentResolver
                       val inputStream = contentResolver.openInputStream(it)
                       val size = inputStream!!.available()
                       if (maxSize != null && size > maxSize) {
                           Toast.makeText(
                               context,
                               "File size should be less than $maxSize MB",
                               Toast.LENGTH_SHORT
                           )
                               .show()
                           return@let
                       }

                       onMediaPicked(it)

                   }
               }


       }



       fun pickMedia() {
           launcher.launch(mimeTypeUser.type)
       }
   }

    class PickMultiMedia{
        private lateinit var launcher: ManagedActivityResultLauncher<String, List<Uri>>
        private lateinit var mimeTypeUser: MimeType

        @SuppressLint("Recycle", "Range", "SuspiciousIndentation")
        @Composable
        fun Init(
            mimeType: MimeType,
            onMediaPicked: (List<Uri>) -> Unit,

            ) {
            mimeTypeUser = mimeType

            launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uri ->
                    onMediaPicked(uri)
                }
        }
        fun pickMedia() {
            launcher.launch(mimeTypeUser.type)
        }
    }

}