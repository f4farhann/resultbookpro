package com.resultbookpro.app.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resultbookpro.app.presentation.common.theme.PrimaryBlue
import com.resultbookpro.app.presentation.common.theme.White

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            contentColor = White,
            disabledContainerColor = PrimaryBlue.copy(alpha = 0.5f)
        ),
        enabled = enabled
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(text = "Login", onClick = {})
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonDisabledPreview() {
    PrimaryButton(text = "Continue", onClick = {}, enabled = false)
}
