package com.fikrilal.narate_mobile_apps.story.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fikrilal.narate_mobile_apps.R
import com.fikrilal.narate_mobile_apps._core.presentation.component.typography.LabelLarge
import com.fikrilal.narate_mobile_apps._core.presentation.theme.BrandColors
import com.fikrilal.narate_mobile_apps._core.presentation.theme.TextColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarPostComponent(
    onClick: () -> Unit,
) {
    Box {
        Column(
        ) {
            TopAppBar(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp),
                title = { },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_narrate),
                        contentDescription = "logo",
                        modifier = Modifier.size(height = 28.dp, width = 100.dp)
                    )
                },
                actions = {
                    OutlinedButton(
                        onClick = onClick,
                        modifier = Modifier,
                        shape = RoundedCornerShape(100),
                        border = BorderStroke(1.dp, Color.Transparent),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = BrandColors.brandPrimary500,
                            contentColor = Color.White
                        )
                    ) {
                        LabelLarge(
                            "Post",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            Divider(
                color = TextColors.grey200,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}