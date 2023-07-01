package com.example.codeglamour.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codeglamour.ui.theme.Purple80
import com.example.codeglamour.util.functions.loginUser
import com.example.codeglamour.util.functions.registerUser
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreenView(
    navController: NavController,
    auth: FirebaseAuth
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isExistingUser by remember { mutableStateOf(false) }
    var isDesigner by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Code:Glamour", textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column (
            horizontalAlignment = Alignment.Start,
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    checked = isExistingUser,
                    onCheckedChange = { isExistingUser = it },
                )
                Text(
                    text = "Existing User",
                )
            }
            if (!isExistingUser) Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = isDesigner,
                    onCheckedChange = { isDesigner = it },
                )
                Text(
                    text = "Are you a Designer?",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(color = Purple80, shape = RoundedCornerShape(size = 4.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .clickable {
                    if (isExistingUser) {
                        loginUser(email, password, auth, navController)
                    } else {
                        registerUser(email, password, auth, navController, isDesigner)
                    }
                }
        ) {
            Text(text = if (isExistingUser) "Login" else "Register")
        }
    }
}
