package com.fikrilal.narate_mobile_apps.auth.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fikrilal.narate_mobile_apps.R
import com.fikrilal.narate_mobile_apps._core.presentation.component.button.CustomButton
import com.fikrilal.narate_mobile_apps._core.presentation.component.textfields.CustomOutlinedTextField
import com.fikrilal.narate_mobile_apps._core.presentation.component.textfields.CustomPasswordTextField
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyLarge
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.BodyMedium
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.HeadingLarge
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.HeadingSmall
import com.fikrilal.narate_mobile_apps._core.presentation.theme.AppColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors

@Composable
fun LoginScreen(navController: NavController) {

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

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
        CustomButton(text = "Masuk", onClick = { /*TODO*/ })
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
                    fontWeight = FontWeight.Bold
                )
            }
        }


    }
}