package com.resultbookpro.app.presentation.common.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resultbookpro.app.presentation.common.theme.BackgroundGray
import com.resultbookpro.app.presentation.common.theme.PrimaryBlue

@Composable
fun TextFieldInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPasswordField: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BackgroundGray,
            unfocusedContainerColor = BackgroundGray,
            disabledContainerColor = BackgroundGray,
            focusedIndicatorColor = PrimaryBlue,
            unfocusedIndicatorColor = Color.LightGray,
        )
    )
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun TextFieldInputPreview() {
    TextFieldInput(value = "", onValueChange = {}, label = "Email")
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun PasswordTextFieldInputPreview() {
    TextFieldInput(value = "password", onValueChange = {}, label = "Password", isPasswordField = true)
}
