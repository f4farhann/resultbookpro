package com.resultbookpro.app.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
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
import com.resultbookpro.app.presentation.common.theme.PrimaryBlue
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme
import com.resultbookpro.app.presentation.common.theme.White

@Composable
fun ProfileScreen(
    onEditProfile: () -> Unit,
    onLogout: () -> Unit
) {
    val menuItems = listOf(
        ProfileMenuItem("Orders", Icons.Default.ShoppingCart),
//        ProfileMenuItem("Customer Care", Icons.Default.HeadsetMic),
        ProfileMenuItem("Invite Friends & Earn", Icons.Default.Share, "You get ₹100 SuperCash for every friend"),
//        ProfileMenuItem("Result Wallet", Icons.Default.Wallet, "Manage rewards and refunds"),
//        ProfileMenuItem("Saved Cards", Icons.Default.CreditCard),
//        ProfileMenuItem("My Rewards", Icons.Default.Stars),
        ProfileMenuItem("Address", Icons.Default.LocationOn),
        ProfileMenuItem("Notifications", Icons.Default.Notifications),
//        ProfileMenuItem("Terms & Conditions", Icons.Default.Description),
//        ProfileMenuItem("Privacy Policy", Icons.Default.PrivacyTip),
        ProfileMenuItem("Who We Are", Icons.Default.Info),
//        ProfileMenuItem("Join Our Team", Icons.Default.GroupAdd)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {
        // User Header Info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F4F8))
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "FH",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "Farhan Haider",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Text(
                    text = "farhanhaider@gmail.com",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Text(
                text = "Edit",
                color = PrimaryBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onEditProfile() }
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(White)
        ) {
            items(menuItems) { item ->
                ProfileMenuRow(item)
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Text("Logout", color = Color.Black, fontWeight = FontWeight.SemiBold)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Version 1.0.0 Build 1",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ProfileMenuRow(item: ProfileMenuItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 16.dp, horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
            if (item.subtitle != null) {
                Text(
                    text = item.subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
    }
}

data class ProfileMenuItem(
    val title: String,
    val icon: ImageVector,
    val subtitle: String? = null
)

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ResultBookProTheme {
        ProfileScreen(onEditProfile = {}, onLogout = {})
    }
}
