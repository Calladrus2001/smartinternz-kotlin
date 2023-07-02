package com.example.vit2_20bce0257

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vit2_20bce0257.ui.theme.VIT2_20BCE0257Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VIT2_20BCE0257Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Bloodbank()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bloodbank() {

    var flag by remember {
        mutableStateOf(false)
    }

    var t1 by remember {
        mutableStateOf("")
    }
    var t2 by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(240, 161, 170))
    ) {
        Image(
            painterResource(id = R.drawable.blood),
            contentDescription = "",
            modifier = Modifier.padding(20.dp)
        )

        Text(
            text = "Welcome Donar !",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = Color.Red,
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Justify
        )


        Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(top = 25.dp, start = 25.dp, end = 25.dp)) {

            Text(
                text = "Enter User Id",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier.padding(5.dp)
            )


            TextField(
                value = t1, onValueChange = { t1 = it }, modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Enter Password",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier.padding(5.dp)
            )

            TextField(
                value = t2, onValueChange = { t2 = it }, modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "''Be the reason for someone's Heartbeat''",
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Red,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)

            )


            Button(onClick = {flag = true}, colors = ButtonDefaults.buttonColors(Color.Red), modifier = Modifier
                .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                .fillMaxWidth(), shape = CutCornerShape(10)
            ) {
                Text(text = "Sign In", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 55.dp,top = 25.dp)) {
                MultiColorText("Don't have account?", Color.Red, " Click Here.", Color.White)

            }



        }

    }

    if(flag == true){
        Second(t1 = t1, t2 =t2)
    }


}



@Composable
fun Second(t1:String, t2:String){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(240, 161, 170))
    ) {
        Scaff()
    }

}


@Composable
fun MultiColorText(text1: String, color1: Color, text2: String, color2: Color) {
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(color = color1)) {
            append(text1)
        }
        withStyle(style = SpanStyle(color = color2,textDecoration = TextDecoration.Underline)) {
            append(text2)
        }
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaff() {
    Scaffold(
        topBar = {mytop()},
        bottomBar = {mybot()},
        content = {a -> mycon(a)}
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mytop() {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement =
                Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = 50.dp
                    )
            ) {
                Icon(
                    Icons.Filled.Menu, contentDescription = "", modifier = Modifier
                        .size(30.dp)
                )

                //Image(painterResource(id = R.drawable.baseline_person_24), contentDescription ="", modifier = Modifier.padding(start = 20.dp))

            }
        },
        modifier = Modifier.drawBehind {
            drawLine(
                Color.Red,
                Offset(0f, size.height),
                Offset(size.width, size.height),
                5f
            )
        }
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mybot() {
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mycon(a: PaddingValues) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement =
                Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = 50
                            .dp
                    )
            ) {
                Icon(
                    Icons.Filled.AccountBox, contentDescription = "", modifier = Modifier
                        .size(30.dp)
                )
                Image(
                    painterResource(id = android.R.drawable.star_big_on),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp)
                )


            }
        },
        modifier = Modifier.drawBehind {
            drawLine(
                Color.Green,
                Offset(0f, size.height),
                Offset(size.width, size.height),
                5f
            )
        }
    )
}