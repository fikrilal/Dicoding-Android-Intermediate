package com.fikrilal.narate_mobile_apps.story.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.LabelLarge
import com.fikrilal.narate_mobile_apps._core.presentation.theme.BrandColors

@Composable
fun CustomOutlinedButton(
    text: String,
    iconPainter: Painter,
    onClick: () -> Unit,
    iconDescription: String? = null,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(100),
        border = BorderStroke(1.dp, BrandColors.brandPrimary300),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = BrandColors.brandPrimary500
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = iconPainter,
                contentDescription = iconDescription,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            LabelLarge(text, color = BrandColors.brandPrimary500)
        }
    }
}

