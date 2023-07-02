package com.example.codeglamour.ui.views

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.codeglamour.util.functions.uploadImageToFirebaseStorage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AddExhibitScreenView(
    navController: NavController
) {
    val imageData = remember { mutableStateOf(Uri.EMPTY) }
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser!!.uid

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            imageData.value = it
        }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        imageData?.let { uri ->
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Gray)
            ) {
                Image(
                    painter = rememberImagePainter(imageData.value),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (imageData.value == Uri.EMPTY) Button(
                onClick = {
                    launcher.launch("image/*")
                }
            ) {
                Text(text = "Select Exhibit From Gallery")
            }
            else Button(
                onClick = {
                    uploadImageToFirebaseStorage(context, imageData.value, uid)
                    navController.popBackStack()
                }
            ) {
                Text(text = "Upload Exhibit")
            }
        }
    }
}