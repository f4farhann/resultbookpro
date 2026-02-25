package com.resultbookpro.app.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultbookpro.app.R
import com.resultbookpro.app.presentation.common.theme.Red
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme

@Composable
fun ProfileScreen(
    onEditProfile: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with user avatar
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Farhan Haider", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "The City School", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Class 12", fontSize = 16.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onEditProfile) {
                Text(text = "Edit Profile")
            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(onClick = onLogout) {
                Text(text = "Logout", color = Red)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ResultBookProTheme {
        ProfileScreen(onEditProfile = {}, onLogout = {})
    }
}
