package com.fikrilal.narate_mobile_apps._core.presentation.component.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrilal.narate_mobile_apps.R
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.LabelLarge
import com.fikrilal.narate_mobile_apps._core.presentation.theme.AppColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.AppShapes
import com.fikrilal.narate_mobile_apps._core.presentation.theme.BrandColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.dmSansFontFamily

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    modifier: Modifier = Modifier,
    iconId: Int? = null,
    iconSize: Dp = 24.dp,
    iconColor: Color = TextColors.grey500,
    placeholderText: String = "",
    placeholderTextStyle: TextStyle = TextStyle(
        color = TextColors.grey500,
        fontSize = 16.sp,
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Normal
    ),
    isSingleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = BrandColors.brandPrimary50,
        focusedContainerColor = BrandColors.brandPrimary100,
        focusedBorderColor = BrandColors.brandPrimary600,
        unfocusedBorderColor = BrandColors.brandPrimary200,
        cursorColor = BrandColors.brandPrimary600,
        focusedTextColor = TextColors.grey700,
        unfocusedTextColor = TextColors.grey700
    ),
    shape: Shape = AppShapes.largeCorners,

    ) {

    Column(
        horizontalAlignment = Alignment.Start,
    ) {
        if (!label.isNullOrEmpty()) {
            LabelLarge(text = label, fontWeight = FontWeight.Normal)
            Spacer(modifier = Modifier.height(8.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = iconId?.let {
                {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "$label Icon",
                        modifier = Modifier.size(iconSize),
                        tint = iconColor
                    )
                }
            },
            modifier = modifier.fillMaxWidth(),
            placeholder = { Text(placeholderText, style = placeholderTextStyle) },
            singleLine = isSingleLine,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = textFieldColors,
            shape = shape,
        )
    }
}

@Composable
fun CustomPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    modifier: Modifier = Modifier,
    iconId: Int? = null,
    iconSize: Dp = 24.dp,
    iconColor: Color = TextColors.grey500,
    placeholderText: String = "",
    passwordLengthRequirement: Int = 8,
    placeholderTextStyle: TextStyle = TextStyle(
        color = TextColors.grey500,
        fontSize = 16.sp,
        fontFamily = dmSansFontFamily,
        fontWeight = FontWeight.Normal
    ),
    isSingleLine: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = BrandColors.brandPrimary50,
        focusedContainerColor = BrandColors.brandPrimary100,
        focusedBorderColor = BrandColors.brandPrimary600,
        unfocusedBorderColor = BrandColors.brandPrimary200,
        cursorColor = BrandColors.brandPrimary600,
        focusedTextColor = TextColors.grey700,
        unfocusedTextColor = TextColors.grey700
    ),
    shape: Shape = AppShapes.largeCorners,
    onInteraction: () -> Unit = {}  // Callback for interaction
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var hasInteracted by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.Start) {
        if (!label.isNullOrEmpty()) {  // Cek jika label tidak null dan tidak kosong
            LabelLarge(text = label, fontWeight = FontWeight.Normal)
            Spacer(modifier = Modifier.height(8.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                hasInteracted = true  // Set has interacted when the user types
            },
            leadingIcon = iconId?.let {
                {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "$label Icon",
                        modifier = Modifier.size(iconSize),
                        tint = iconColor


                    )
                }
            },
            modifier = modifier.fillMaxWidth(),
            placeholder = { Text(placeholderText, style = placeholderTextStyle) },
            singleLine = isSingleLine,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            keyboardActions = keyboardActions,
            colors = textFieldColors,
            shape = shape,
            trailingIcon = {
                val iconRes = if (passwordVisible) R.drawable.ic_hide_icon else R.drawable.ic_show_icon
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible; onInteraction() }) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = description,
                        modifier = Modifier.size(iconSize),
                        tint = iconColor
                    )
                }
            }
        )
        if (hasInteracted && value.length < passwordLengthRequirement) {
            Text(
                "Password must be at least $passwordLengthRequirement characters",
                color = AppColors.errorColor
            )
        }
    }
}