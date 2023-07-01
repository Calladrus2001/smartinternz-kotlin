package com.example.codeglamour.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

@Composable
fun ProfileScreenView(
    navController: NavController
) {
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    val isDesigner = remember { mutableStateOf(false) }
    val imageUrls = remember { mutableStateListOf<String>() }
    val firestore = Firebase.firestore
    var context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp, horizontal = 8.dp)
    ) {
        LaunchedEffect(uid) {
            val userRef = firestore.collection("users").document(uid)
            val documentSnapshot = userRef.get().await()
            if (documentSnapshot.exists()) {
                val data = documentSnapshot.data
                isDesigner.value = data?.get("isDesigner") as Boolean
                val imageUrlList = data?.get("images") as? List<String>
                imageUrls.addAll(imageUrlList.orEmpty())
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 6.dp, vertical = 4.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(5.dp)
                    )


            ) {
                Text(uid, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }

            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 6.dp, vertical = 4.dp)

            ) {
                Text(if (isDesigner.value) "Designer" else "Consumer", style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "My Exhibits",
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary, fontSize = 16.sp,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(imageUrls) { imageUrl ->
                var imageLoaded by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier.aspectRatio(1f)
                ) {
                    if (imageLoaded) {
                        Image(
                            painter = rememberImagePainter(imageUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                // Update imageLoaded state once the image is loaded
                LaunchedEffect(imageUrl) {
                    val request = ImageRequest.Builder(context)
                        .data(imageUrl)
                        .build()
                    val result = ImageLoader(context).execute(request)
                    imageLoaded = result is SuccessResult
                }
            }
        }
    }
}