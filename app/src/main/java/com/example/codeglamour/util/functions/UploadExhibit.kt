package com.example.codeglamour.util.functions

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImageToFirebaseStorage(context: Context, uri: Uri, uid: String) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference.child(uid)
    val fileExtension = getFileExtension(context, uri)
    val imageName = "${UUID.randomUUID()}.$fileExtension"
    val imageRef = storageRef.child("images/$imageName")

    val uploadTask = imageRef.putFile(uri)

    uploadTask.continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let {
                throw it
            }
        }
        imageRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUrl = task.result.toString()
            saveImageUrlToFirestore(uid, downloadUrl)
            showToast(context, "Image uploaded successfully!")
        } else {
            showToast(context, "Image upload failed: ${task.exception?.message}")
        }
    }
}

fun saveImageUrlToFirestore(uid: String, imageUrl: String) {
    val firestore = FirebaseFirestore.getInstance()
    val userRef = firestore.collection("users").document(uid)

    userRef.update("images", FieldValue.arrayUnion(imageUrl))
        .addOnSuccessListener {
            Log.d("Firestore", "Image URL added to Firestore")
        }
        .addOnFailureListener { exception ->
            Log.e("Firestore", "Failed to add image URL: ${exception.message}")
        }
}


fun getFileExtension(context: Context, uri: Uri): String {
    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(uri)
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        ?: throw IllegalArgumentException("Unsupported file extension")
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
