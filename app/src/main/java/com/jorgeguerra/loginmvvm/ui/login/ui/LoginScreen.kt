package com.jorgeguerra.loginmvvm.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jorgeguerra.loginmvvm.R
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(14.dp)
    ) {
        Login(Modifier.align(Alignment.Center), viewModel)
    }

}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {

    val email :String by viewModel.email.observeAsState(initial = "")
    val password :String by viewModel.password.observeAsState(initial = "")
    val loginEnable :Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading :Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if (isLoading){
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }else{
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            HeaderImage()
            Spacer(Modifier.height(30.dp))
            EmailField(email) { viewModel.onLoginChanged(it, password) }
            Spacer(Modifier.height(4.dp))
            PasswordField(password){ viewModel.onLoginChanged(email, it) }
            Spacer(Modifier.height(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(Modifier.height(16.dp))
            LoginButton(loginEnable){
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
//        Spacer(Modifier.height(16.dp))
//        LoginDivider()
//        Spacer(Modifier.height(16.dp))
//        SocialLogin()

        }
    }
}

@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = {onLoginSelected()},
        enabled = loginEnable,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF52BACD),
            disabledContainerColor = Color(0xFA9B9B9B),
            disabledContentColor = Color.White,
            contentColor = Color.White
            )
        )
        {
            Text( text = "Login")
        }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Olvidaste la contrasena?",
        modifier = modifier.clickable { },
        color = Color(0xFF4CBED7),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged:(String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Black,
            focusedBorderColor = Color.Green,
            cursorColor = Color.Red
        )
    )
}

@Composable
fun EmailField(email: String, onTextFieldChanged:(String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = {onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Black,
            focusedBorderColor = Color.Green,
            cursorColor = Color.Red
        )
    )
}


@Composable
fun HeaderImage() {
    Image(
        painter = painterResource(id = R.drawable.imagen),
        contentDescription = "logo",
        Modifier
            .size(150.dp)
            .clip(shape = RoundedCornerShape(25f)),
    )

}
