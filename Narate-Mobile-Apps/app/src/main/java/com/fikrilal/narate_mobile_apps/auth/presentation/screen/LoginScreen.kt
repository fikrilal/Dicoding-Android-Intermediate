package com.fikrilal.narate_mobile_apps.auth.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fikrilal.narate_mobile_apps.R
import com.fikrilal.narate_mobile_apps._core.presentation.component.button.CustomButton
import com.fikrilal.narate_mobile_apps._core.presentation.component.textfields.CustomOutlinedTextField
import com.fikrilal.narate_mobile_apps._core.presentation.component.textfields.CustomPasswordTextField
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyLarge
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyMedium
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.HeadingLarge
import com.fikrilal.narate_mobile_apps._core.presentation.theme.AppColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.BrandColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors
import com.fikrilal.narate_mobile_apps.auth.presentation.viewmodel.AuthResult
import com.fikrilal.narate_mobile_apps.auth.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.loginState) {
        viewModel.loginState.collect { result ->
            when (result) {
                is AuthResult.Loading -> {
                    isLoading.value = true
                    Log.d("LoginScreen", "Loading: Attempting to log in.")
                }

                is AuthResult.Success -> {
                    isLoading.value = false
                    Log.d("LoginScreen", "Success: Logged in successfully.")
                    navController.navigate("HomeScreen")
                }

                is AuthResult.Error -> {
                    isLoading.value = false
                    Log.e(
                        "LoginScreen",
                        "Error: Login failed with message - ${result.exception.message}"
                    )
                    Toast.makeText(
                        context,
                        "Login failed: ${result.exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> isLoading.value = false
            }
        }
    }

    if (isLoading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = BrandColors.brandPrimary500)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .padding(horizontal = 20.dp, vertical = 20.dp)

        ) {
            Spacer(modifier = Modifier.heightIn(15.dp))
            Image(
                painter = painterResource(id = R.drawable.logo_narrate),
                contentDescription = "logo",
                Modifier.size(height = 32.dp, width = 120.dp)
            )
            Spacer(modifier = Modifier.heightIn(24.dp))
            HeadingLarge(text = "Masuk Ke Akun Kamu")
            Spacer(modifier = Modifier.heightIn(4.dp))
            BodyLarge(
                text = "Masuk untuk mengakses seluruh fitur narrate.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = TextColors.grey600
            )

            Spacer(modifier = Modifier.heightIn(24.dp))
            CustomOutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = "Email",
                iconId = R.drawable.ic_email_icon,
                email = true,
                placeholderText = "Enter your email"
            )
            Spacer(modifier = Modifier.heightIn(16.dp))
            CustomPasswordTextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = "Password",
                iconId = R.drawable.ic_password_icon,
                placeholderText = "Enter your password"
            )
            Spacer(modifier = Modifier.heightIn(16.dp))
            Spacer(modifier = Modifier.heightIn(40.dp))
            CustomButton(text = "Masuk", onClick = {
                if (emailState.value.isBlank() || passwordState.value.isBlank()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    scope.launch {
                        Log.d("LoginScreen", "Attempting to log in with email: ${emailState.value}")
                        viewModel.login(emailState.value, passwordState.value)
                    }
                }
            })
            Spacer(modifier = Modifier.weight(2f))
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(

                ) {
                    BodyMedium(
                        text = "Belum punya Akun?",
                        color = TextColors.grey500,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.widthIn(5.dp))
                    BodyMedium(
                        text = "Daftar",
                        color = AppColors.linkColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { navController.navigate("RegisterScreen") }
                    )
                }
            }
        }
    }
}