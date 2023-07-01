package com.example.codeglamour.util.functions

import android.content.ContentValues.TAG
import android.util.Log
import androidx.navigation.NavController
import com.example.codeglamour.util.classes.Screens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun registerUser(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
    isDesigner: Boolean
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val db = Firebase.firestore
                val user = hashMapOf(
                    "emailID" to auth.currentUser?.email,
                    "uid" to auth.currentUser?.uid,
                    "isDesigner" to isDesigner,
                    "images" to listOf<String>()
                )

                db.collection("users").document(auth.currentUser!!.uid)
                    .set(user)
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot added")
                        navController.navigate(Screens.MainScreen.route)
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            } else {
                Log.d("Auth", "Failed: ${task.exception}")
            }
        }
}

fun loginUser(email: String, password: String, auth : FirebaseAuth, navController: NavController){
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (task.isSuccessful) navController.navigate(Screens.MainScreen.route)
        else {
            Log.d("Auth", "Failed: ${task.exception}")
        }
    }
}