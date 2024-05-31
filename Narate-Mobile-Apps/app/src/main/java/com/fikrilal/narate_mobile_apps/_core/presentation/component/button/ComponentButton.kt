package com.fikrilal.narate_mobile_apps._core.presentation.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.LabelMedium
import com.fikrilal.narate_mobile_apps._core.presentation.theme.AppShapes
import com.fikrilal.narate_mobile_apps._core.presentation.theme.BrandColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.dmSansFontFamily

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    padding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
    shape: RoundedCornerShape = AppShapes.mediumCorners, // Default shape
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        contentPadding = padding,
        colors = ButtonDefaults.buttonColors(
            containerColor = BrandColors.brandPrimary600,
            contentColor = Color.White
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let {
                Icon(icon, contentDescription = "Button $text", tint = Color.Unspecified) // Assuming you pass a description if needed elsewhere
                LabelMedium(text = " ", modifier = Modifier, color = Color.White) // Spacer hack
            }
            LabelMedium(
                text = text,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = dmSansFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    MaterialTheme {
        CustomButton(
            text = "Click Me",
            onClick = {},
            shape = AppShapes.largeCorners // Using large corners for preview
        )
    }
}
