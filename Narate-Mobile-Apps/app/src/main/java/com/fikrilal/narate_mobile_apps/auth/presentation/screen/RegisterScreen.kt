package com.fikrilal.narate_mobile_apps.auth.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors
import com.fikrilal.narate_mobile_apps.auth.presentation.viewmodel.RegisterViewModel
import com.fikrilal.narate_mobile_apps.auth.presentation.viewmodel.AuthResult
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val passwordRepeatState = remember { mutableStateOf("") }

    LaunchedEffect(viewModel.registerState) {
        viewModel.registerState.observeForever { result ->
            when (result) {
                is AuthResult.Loading -> {
                    // Show loading indicator
                    Log.d("RegisterState", "Loading")
                }
                is AuthResult.Success -> {
                    // Navigate to success screen
                    Log.d("RegisterState", "Success")
                    navController.navigate("HomeScreen")
                }
                is AuthResult.Error -> {
                    // Show error message
                    Log.e("RegisterState", "Error: ${result.exception.message}")
                    Toast.makeText(context, "Registration failed: ${result.exception.message}", Toast.LENGTH_LONG).show()
                }
                else -> { /* No-op */ }
            }
        }
    }

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
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = "Nama Lengkap",
            iconId = R.drawable.ic_profile_icon,
            placeholderText = "Enter your email"
        )
        Spacer(modifier = Modifier.heightIn(16.dp))
        CustomOutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = "Email",
            iconId = R.drawable.ic_email_icon,
            placeholderText = "Enter your email"
        )
        Spacer(modifier = Modifier.heightIn(16.dp))
        CustomPasswordTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = "Kata Sandi",
            iconId = R.drawable.ic_password_icon,
            placeholderText = "Enter your password"
        )
        Spacer(modifier = Modifier.heightIn(16.dp))
        CustomPasswordTextField(
            value = passwordRepeatState.value,
            onValueChange = { passwordRepeatState.value = it },
            label = "Ulangi Kata Sandi",
            iconId = R.drawable.ic_password_icon,
            placeholderText = "Enter your password"
        )
        Spacer(modifier = Modifier.heightIn(40.dp))
        CustomButton(text = "Daftar", onClick = {
            if (emailState.value.isBlank() || passwordState.value.isBlank() || passwordRepeatState.value.isBlank()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@CustomButton
            }
            if (passwordState.value != passwordRepeatState.value) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@CustomButton
            }
            // Call registration logic
            scope.launch {
                try {
                    viewModel.register(
                        name = nameState.value,
                        email = emailState.value,
                        password = passwordState.value
                    )
                    // Navigate or show success message
                    navController.navigate("SuccessRoute") // Update with your actual success route
                } catch (e: Exception) {
                    Toast.makeText(context, "Registration failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
        Spacer(modifier = Modifier.weight(0.1f))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            BodyMedium(
                text = "Belum punya Akun?",
                color = TextColors.grey500,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.widthIn(5.dp))
            BodyMedium(
                text = "Login",
                modifier = Modifier.clickable { navController.navigate("LoginScreen") },
                color = AppColors.linkColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
